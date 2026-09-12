package smartPark.smart_park.migration;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Vérifie que les migrations construisent le schéma complet à partir de rien.
 *
 * <p>Sur la base de développement, V1 n'est jamais exécutée : elle est reprise
 * comme baseline. Sans ce test, une erreur dans V1 ne se manifesterait qu'à la
 * première installation sur une base vierge — c'est-à-dire au pire moment.
 *
 * <p>Le test déroule les migrations dans un schéma jetable, puis le supprime.
 */
@SpringBootTest
class MigrationDepuisZeroTest {

    private static final String SCHEMA_JETABLE = "verif_migration_depuis_zero";

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void lesMigrationsConstruisentLeSchemaComplet() {
        jdbcTemplate.execute("DROP SCHEMA IF EXISTS " + SCHEMA_JETABLE + " CASCADE");
        try {
            Flyway flyway = Flyway.configure()
                    .dataSource(dataSource)
                    .schemas(SCHEMA_JETABLE)
                    .locations("classpath:db/migration")
                    .createSchemas(true)
                    .load();

            flyway.migrate();

            assertThat(flyway.info().current().getVersion().toString()).isEqualTo("2");
            assertThat(tablesDuSchemaJetable()).contains(
                    "agence", "categorie", "utilisateurs", "immobilisations",
                    "detail_vehicules", "interventions", "tickets", "transactions",
                    "one_time_passwords", "logiciel");

            // La colonne rendue optionnelle pour le mobilier doit l'être aussi
            // sur une base neuve, pas seulement après rattrapage.
            assertThat(numeroSerieEstNullable()).isTrue();
        } finally {
            jdbcTemplate.execute("DROP SCHEMA IF EXISTS " + SCHEMA_JETABLE + " CASCADE");
        }
    }

    private List<String> tablesDuSchemaJetable() {
        return jdbcTemplate.queryForList(
                "SELECT table_name FROM information_schema.tables WHERE table_schema = ?",
                String.class, SCHEMA_JETABLE);
    }

    private boolean numeroSerieEstNullable() {
        String nullable = jdbcTemplate.queryForObject(
                "SELECT is_nullable FROM information_schema.columns "
                        + "WHERE table_schema = ? AND table_name = 'immobilisations' AND column_name = 'numero_serie'",
                String.class, SCHEMA_JETABLE);
        return "YES".equals(nullable);
    }
}
