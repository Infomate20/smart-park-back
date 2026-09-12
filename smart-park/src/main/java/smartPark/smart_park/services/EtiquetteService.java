package smartPark.smart_park.services;

import java.io.IOException;
public interface EtiquetteService {


    /**
     * Génère un PDF formaté en étiquette pour une immobilisation donnée.
     * @param immobilisationId L'ID de l'immobilisation pour laquelle générer l'étiquette.
     * @return Un tableau de bytes représentant le fichier PDF.
     * @throws IOException si une erreur d'I/O se produit.
     * @throws smartPark.smart_park.exceptions.ResourceNotFoundException si l'immobilisation n'est pas trouvée.
     */
    public byte[] genererEtiquettePdf(Long immobilisationId)throws IOException;


}
