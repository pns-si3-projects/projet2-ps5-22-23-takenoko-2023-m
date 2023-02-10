# Projet Takenoko - Groupe M
Membres de l'équipe : 

 - Maurois Quentin
 - Dagues de La Hellerie Krysto
 - Stenguel Damien
 - Beurel Simon  

Ce document à pour but de servir de documentation vis-à-vis du projet Takenoko réalisé par l'équipe M de Polytech Nice-Sophia. 
## Point d'avancement : 
Voici un tableau résumant les différentes fonctionnalités réalisées sur le projet Takenoko : 
|Fonctionnalité |Etat de réalisation  |
|--|--|
|Plateau de jeu avec des tuiles hexagonales|Réalisé  |
|3 Types d'objectifs (Panda, Jardinier, Pattern) | Réalisé
|Comportement de Bots lors d'une partie | Réalisé
|Irrigations | Réalisé
|Lancement du dé et ses conséquences|Réalisé
|Implémentation des règles avec les bâtiments|Réalisé

Résumé pour le choix des logs : 
Au début de la réalisation de ce projet nous avons utilisé naivement de vulgaires System.out.println() comme beaucoup d'étudiants dans notre cas. Cependant, suite à la demande de Mr Collet, tout ces println() ont été remplacés grâce à la librairie Java java.util.loggin. Pour ce faire, nous avons du trier les messages qu'il faut afficher  en fonction de leur ordre d'importance, nous avons donc décidé de mettre tous les messages liés à l'information et au déroulement d'une partie comme par exemple : 

> Le joueur Simon déplace le panda sur la case -1,0

en message de type Level.info, car nous avons considéré que ce message était de bas niveau concernant l'importance.  Cependant, pour le message de fin annonçant le vainqueur de la partie (ou l'égalité)

> Le joueur Quentin gagne la partie avec 30 points !

nous avons attribué à ce message un Level.severe car nous avons considéré que ce message était d'une grande importance. 
Ainsi par conséquent, si l'utilisateur ne souhaite voir apparaître que le message de victoire/égalité il pourra régler le niveau du Logger sur SEVERE, sinon s'il souhaite voir tous les détails il pourra le mettre sur INFO


Résumé pour les statistiques CSV : 
Les statistiques présentes dans le fichier csv permet notamment de comprendre quel bot est supérieur à quel autre bot. Nous pouvons trouver dans ce fichier des statistiques comme le nombre de parties jouées, le nombre de parties gagnés pour chaque bot ainsi que le pourcentage de victoire. 

![Exemple de génération d'un fichier csv sur 5575 parties avec comme stats par ligne : 1)Le nom du joueur 2) Le nombre de parties gagnées 3) Le pourcentage de victoire](https://cdn.discordapp.com/attachments/701053516256509954/1073158223638568980/image.png)

Résumé de ce que a été fait pour le bot : 
Actuellement nous avons deux versions du bot, qui sont toutes les deux différentes :

 1. Le premier bot va fonctionner de cette manière : (1) Il va regarder l'objectif qui vaut le plus de points => (2) Il va lancer une méthode propre à cet objectif => (3) il répète l'étape (1)
 2. Pour ce qui est du deuxième bot, ce dernier est beaucoup plus évolué car il va  - comme pour le premier - regarder l'objectif, il va essayer de l'accomplir mais s'il voit qu'il ne peut pas accomplir cet objectif, il va essayer d'en accomplir un autre (le second qui rapporte le plus de points). Ce bot est assez complexe, c'est pour cela qu'avant de nous lancer dans la programmation, nous avons essayer de modéliser sur un schéma l'intelligence de ce bot : 
 ![Schéma permettant de comprendre la réflexion pour gagner le jeu Takenoko](https://cdn.discordapp.com/attachments/701053516256509954/1072517259068723220/image.png)

## Architecture et qualité
Comment est faite l'architecture du projet : 
Pour l'architecture du projet, nous avons créer un package Bot notamment pour pouvoir y mettre les différents types de bots que nous avons codé. 
(à continuer)

Où trouver les infos : (documentation) 
Toute la documentation de notre projet se trouver dans le dossier doc dans lequel vous avez pu trouver ce fichier. Il est également possible de pouvoir trouver la javadoc du projet qui a été générée. Celle-ci va pouvoir vous permettre de mieux comprendre le fonctionnement des différentes méthodes et de comment ces dernières fonctionnent. 

Etat de base du code :
Actuellement, nous savons que le projet possède une bonne base du jeu Takenoko, les règles principales sont implémentées et notamment des fonctionnalités propres au jeu comme les irrigations, la détection de patterns pour les objectifs Parcelles fonctionne correctement. Nous sommes également en mesure d'affirmer que les différents bots que nous avons créer fonctionnent correctement et ont chacun une manière de réfléchir qui leur est propre. 
Cependant, il reste des points négatifs dans ce code. Par exemple, nous savons que ce code est difficilement maintenable et que pour l'équipe qui reprendra ce code, il faudra une phase d'adaptation et de compréhension du code pour bien comprendre son fonctionnement. De plus, il est également possible de réaliser une grosse phase de refactorisation dans ce projet car nous savons notamment grâce à SonarCube qu'il est possible de refactoriser ce code, cependant, à cause d'un manque de temps, nous avons pas pu effectuer cette étape de manière complète.
Nous conseillons donc à la future équipe qui reprendra ce projet, de prévoir une bonne semaine d'adaptation pour pouvoir bien relire le code/la javadoc, bien comprendre le fonctionnement du programme, et effectuer une refactorisation pour pouvoir repartir sur de bonnes bases.
## Processus 
Responsabilités de l'équipe : 
Voici un tableau qui répartit la responsabilité de chacun sur une partie du projet. Il est cependant important de noter que bien que nous nous sommes spécialisés chacun sur une partie du projet, tout le monde à "toucher à tout" au projet.
|Nom Prénom|Responsabilité  |
|--|--|
|  Maurois Quentin|Irrigations, SonarCube, Coordonnées |
|Stenguel Damien| Détection des patterns, Refactorisation
|Dagues de La Hellerie Krysto| Dé, Météo, Bâtiments, Piles d'objectifs, Objectifs|
|Beurel Simon| Comportement du bot, Différentes méthodes de réflexion du bot

Le process de l'équipe : 
 - Nous avons travailler en utilisant la plateforme Github pour pouvoir stocker les différents fichiers de ce projet. Grâce à cette plateforme, nous avons pu créer différentes Millestones qui contiennent chacunes différentes issues. 
 -  La branche principale, qui se nomme *"main"* sert de branche correspondante aux différente releases. Il existe une autre branche qui s'appelle *"develop"* qui va nous servir de point de départ pour les différentes features que l'on veut réaliser. 
Pour toutes les nouvelles branches qui sont crées, elles possèdent toutes le format de nommage suivant : *feature-nom-de-la-feature*
 - Concernant les pull requests, elles sont obligatoires pour raccorder une branche à celle *develop* ou alors à la branche *main*, toutes les pull requests nécessitent au moins 2 approbations de personnes différentes. Ce choix à été fait car comme nous étions 4 dans le groupe, cela permettait d'avoir 3 personnes (2 qui review la pull request + l'auteur) qui lisent chacune des fonctionnalités. 
