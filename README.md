For those who would like to see how works this little application, you can download the script of the database if you want to use the mySQL part.
I used the jre1.8.0_261 but if you don't have it, remember to have a jdk like 14 or 13 (you can check here : [https://www.oracle.com/fr/java/technologies/javase-downloads.html](https://www.oracle.com/fr/java/technologies/javase-downloads.html))
Remember to check the buildpath and to change the dbname in connexion/Connexion.java
![alt text](https://ibb.co/g7tgRpV)

# **Compte rendu des réalisations du projet**

# CPOA_THIL_BRUNGARD_Projet
Une choiceBox permettant le changement de persistance est disponible pour toutes les pages (une actualisation des données est nécessaire après un changement de persistance)

CE QUI NE FONCTIONNE PAS :
-lien vers une image pour les catégories

Ce qui fonctionne :
PRODUITS :
- affichage des produits
- ajout d'un produit qui possède un nom unique (pas de duplicata)
- modification d'un produit (changement de nom possible uniquement si le nom est unique)
- suppression d'un produit (demande de confirmation activée)  possible uniquement si le produit n'est dans aucune ligne de commande
- bouton pour actualiser les données après la modification d'une catégorie / quantité commandée
- tri (ordre croissant OU décroissant) des données par nom / tarif / titre de catégorie / quantité commandée
- filtrage des données disponible pour nom / tarif / titre de catégorie
- bouton ajout activé uniquement lorsqu'aucune ligne est séléctionnée
- boutons modification / suppression activés uniquement lorsqu'une ligne est séléctionnée


CATEGORIE : 
- affichage des catégories
- ajout d'une catégorie qui possède un titre unique (pas de duplicata)
- modification d'une catégorie (changement de titre possible uniquement si le titre est unique)
- suppression d'une catgéorie (demande de confirmation activée) possible uniquement si aucun produit n'est issu de cette catégorie
- bouton pour actualiser les données
- tri (ordre croissant OU décroissant) des données par titre / visuel
- filtrage des données disponible pour titre / visuel
- bouton ajout activé uniquement lorsqu'aucune ligne est séléctionnée
- boutons modification / suppression activés uniquement lorsqu'une ligne est séléctionnée


CLIENT : 
- affichage des clients
- ajout d'une client unique (pas de duplicata)
- modification d'un client 
- suppression d'un client possible uniquement s'il n'a passé aucune commande
- tri des données par nom, ville
- filtrage des clients par nom ou nom, prénom
- bouton ajout activé uniquement lorsqu'aucune ligne est séléctionnée
- boutons modification / suppression activés uniquement lorsqu'une ligne est séléctionnée
- à partir de la fiche détail du client, possibilité de voir ses commandes

COMMANDE : 
- affichage des commandes
- ajout d'une commande unique (pas de duplicata : une seule commande par client et par jour)
- si commande déjà existante, on propose d'ajouter directement dans celle-ci
- modification d'une commande
- suppression d'une commande après une demande de confirmation
- bouton ajout activé uniquement lorsqu'aucune ligne est séléctionnée
- boutons modification / suppression activés uniquement lorsqu'une ligne est séléctionnée
- à partir de la fiche détail de la commande, visualisation des lignes de commandes
- possibilité de filtrer les commandes par client ou par produit 

 LIGNE COMMANDE : 
- à partir de la fiche détail de la commande, affichage des lignes de commandes
- ajout d'une ligne de commande unique (pas de duplicata)
- si ligne de commande déjà existante, on indique à l'utilisateur de passer par l'option modifier
- modification d'une ligne de commande
- suppression d'une ligne de commande après une demande de confirmation
- bouton ajout activé uniquement lorsqu'aucune ligne est séléctionnée
- boutons modification / suppression activés uniquement lorsqu'une ligne est séléctionnée


Répartition du travail:
- Le design et la réalisation de l'application ont été réalisé par Luc BRUNGARD
- Tout ce qui concerne les produits et catégories ont été géré par Luc BRUNGARD
- Tout ce qui concerne les clients et commandes et lignes de commandes ont été géré par Claire THIL

Notre répartition du travail serait de 50% pour Luc BRUNGARD et 50% pour Claire THIL 

Explication :
- Les parties produit + catégorie sont moins conséquentes que les partis clients + commandes + lignes de commandes
- pour palier à cette inégalité, Luc BRUNGARD a réaliser le design de l'application 
