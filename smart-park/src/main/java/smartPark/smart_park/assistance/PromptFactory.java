package smartPark.smart_park.assistance;

import org.springframework.stereotype.Component;

@Component
public class PromptFactory {
    /**
     * Construit le prompt système qui guide l'IA pour la génération de SQL.
     * C'est la partie la plus importante pour garantir des résultats corrects et sécurisés.
     * @param userQuestion La question de l'utilisateur en langage naturel.
     * @return Le prompt complet à envoyer à l'API de Groq.
     */

    public String createSqlGenerationPrompt(String userQuestion) {

        String schemaDescription = """
            You are a PostgreSQL expert. Your task is to convert a user's question into a valid PostgreSQL SELECT query.
            You must adhere to the following rules:
            1. ONLY generate a single, valid PostgreSQL SELECT statement.
            2. DO NOT generate any other text, explanations, or markdown. Only the SQL query without semi-colon.
            3. NEVER generate commands that modify the database, such as INSERT, UPDATE, DELETE, DROP, ALTER, etc.
            4. Use ONLY the following tables and columns:
               - table `immobilisations`: id, code_immobilisation, numero_serie, designation, marque, modele, date_acquisition, prix_acquisition, etat, actif,duree_garantie_mois,marque,modele,valeur_actuelle, categorie_id, agence_id
               - table `agence`: id, code, nom, ville, pays,actif, adresse,code_postal,description,nom_responsable,pays
               - table `utilisateurs`: id, nom_utilisateur, prenom,poste, email, role, agence_id
               - table `categorie`: id, nom, code, description, duree_amortissement_mois, methode_amortissement, actif
               - table `detail_vehicules`: id, immobilisation_id, immatriculation, kilometrage, type_carburant, puissance_fiscale, nombre_places, date_dernier_controle_technique, date_prochain_controle_technique, compagnie_assurance, numero_police_assurance, date_expiration_assurance, observations
               - table `interventions`:description, date_intervention, cout_intervention, type_intervention, etat_intervention, immobilisation_id, technicien_id,date_cloture,observation,technicien_id,orientation
               - table `tickets`: description_probleme, date_creation, etat, immobilisation_id, demandeur_id,motif_rejet,intervention_id,validateur_id
               - table `transactions`:created_at,date_demande,date_validation,etat_transaction,motif,motif_rejet,observations,type_transaction,agence_destination_id, agence_source_id,demandeur_id,immobilisation_id,validateur_id
            5. For dates, you can use functions like EXTRACT(YEAR FROM date_column) and CURRENT_DATE.
            6. If the user asks a question that cannot be answered with the provided schema, or if it's ambiguous, return the text "ERROR: Question cannot be answered with the available data."
            7. For calculates fields, you can use any fonctions aviables for postgres.
            """;

        return schemaDescription + "\n\nUser question: \"" + userQuestion + "\"\n\nPostgreSQL Query:";
    }
}
