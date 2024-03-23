# Projet de programmation : PacMan

L'objectif de ce projet est de reproduire le jeu historique PacMan en programmation orientée objet Java en restant au plus proche du fonctionnement du jeu d'origine.

## Principe général

Dans le jeu PacPan, le ou la joueuse contrôle un petit personnage qui se déplace dans un labyrinthe pour manger des points blancs. Son objectif est de manger tous les points blancs. Il est poursuivi par des fantômes. Si un fantôme attrape PacMan, on perd une vie. Si on n'a plus de vie, le jeu est terminé. Au contraire, si PacMan mange tous les points blancs, le niveau est gagné et l'on passe au niveau suivant. Dans le jeu d'origine, tous les niveaux utilisent le même labyrinthe mais les propriétés (vitesse, comportement des fantômes, etc.) ne sont pas exactement les mêmes.

Pour le fonctionnement précis du jeu, on se basera sur le [PacMan dossier](https://pacman.holenet.info/)  écrit par Jamey Pittman.

## Organisation du code

Le code est organisé selon 3 packages :

* `model` : contient tout ce qui s'occupe des données du plateau et de la modélisation des agents
  - `actors` : les différents agents du plateau (PacMan, les fantômes et les bonus)
  - `maze` : le labyrinthe dans lequel évoluent les acteurs
  - `board` : le plateau contenant à la fois le labyrinthe, les acteurs et des données comme le score et le niveau courant
* `view` : l'interface graphique
* `control` : le contrôleur reliant l'interface graphique au modèle

Dans chacuns de ces packages, vous trouverez quelques classes utilitaires qui vous sont fournies et surtout des **interfaces** qui ont servi de base pour l'ensemble des tests. Vous développerez votre jeu en implémentant les interfaces fournies.

## Tests

Les tests sont proposés dans le package `test`, ils sont divisés en 4 parties qui correspondent aux étapes de développement du projet décrites ci-dessous. A noter que les tests sont recopiés d'une étape à l'autre et ne sont pas rétrocompatibles. Autrement dit, au fur et à mesure des étapes, certains tests des étapes des étapes précédentes ne fonctionneront plus et c'est normal. 

**Vous avez le droit de modifier les interfaces et les tests si vous le jugez nécessaire. Il faudra dans ce cas le justifier dans votre rapport.**

## Étapes du projet

Malgré son apparente simplicité, le jeu PacMan a de très nombreuses subtilités qu'il n'est pas toujours facile de reproduire à l'identique. Nous allons développer le jeu par étape en faisant évoluer au fur et à mesure à la fois le modèle et l'interface graphique (et donc le contrôleur). 

En terme d'évaluation, l'étape 1 sera nécessaire pour la validation du projet (10 / 20) et l'étape 4 voire plus pour la note maximale. L'évaluation est pensé en ces termes : l'objectif est qu'il soit possible pour toutes et tous d'obtenir la moyenne en travaillant de façon sérieuse mais que chacun et chacune se motive et soit récompensé pour aller le plus loin possible.

Pour se faire une idée : dès l'étape 1, on aura une interface graphique fonctionnelle. Il faudra attendre l'étape 2 pour avoir un début de "vrai jeu". A la fin de l'étape 3, la différence avec le vrai PacMan sera minime et l'étape 4 permet d'ajouter des subtilités.

### Étape 1

Notre premier objectif est d'obtenir un plateau de jeu fonctionnel avec un labyrinthe dans lequel on peut contrôler PacMan au clavier. Pour l'instant, il n'y aura pas de fantômes, ni de score, ni de niveau etc.

En résumé, à faire dans l'ordre pour cette étape :

* La classe implantant le labyrinthe `Maze` 
* une première implantation simples des plateaux de jeux (test et classique) sans PacMan
* une première interface graphique / contrôleur 
* PacMan et son interaction avec le plateau
* le reste de l'interface graphique pour le contrôle de PacMan

Dans toutes les interfaces définies, on liste précisément les méthodes qui seront utiles à l'étape 1. Pour les autres méthodes, vous pouvez vous contenter de créer une implémentation par défaut qui ne fait rien et vous compléterez plus tard. 

#### Côté modèle : le labyrinthe "Maze"

La première chose à faire est de programmer le modèle du labyrinthe. Je vous invite ainsi à lire la partie [Maze logic](https://pacman.holenet.info/#Chapter_3) du PacMan Dossier.

Qu'est-ce qu'on y découvre ? Le labyrinthe est en fait composé de tuiles qui peuvent être des murs ou non. Les acteurs se déplacent sur ces tuiles. Quelle que soit son apparence graphique, la position d'un acteur est modélisée par un point (un pixel) et occupe la tuile sur laquelle est ce point.

La majeure partie du code relatif au labyrinthe est à réaliser dès l'étape 1, les méthodes attendues sont décrites et commentées dans l'interface `Maze` du package `model.maze` avec des tests correspondants.

Par ailleurs, nous fournissons deux classes utilitaires :

* `TilePosition` permet simplement d’enregistrer la position d'une tuile dans le labyrinthe. 
* `Tile` est un type énuméré qui liste les tuiles possibles et offre des méthodes de bases sur chacune des tuiles.

Concrètement : on considère le labyrinthe comme un tableau 2 dimensions de tuiles. La taille des tuiles en pixels (8x8) est sauvegardée dans des constantes de l'interface (qui sont par défaut publics et finaux). On sauvegarde aussi la position du centre de la tuile (3,3) qui servira au moment de calculer le mouvement des agents sur le plateau.

Vous devez implantez une classe implémentant l'interface qui contiendra donc le tableau comme un champs privé et interagira avec le reste du jeu grâce aux méthodes de l'interface.

On vous demande aussi **deux méthodes statiques** permettant de charger un labyrinthe : la méthode chargeant un labyrinthe vide et chargeant un labyrinthe depuis un fichier. Vous trouverez deux exemples de fichiers dans le dossier "resources" : `maze.txt` et `test.txt`. 

Le Labyrinthe `maze.txt` correspond au labyrinthe classique du jeu PacMan. Notez que nous avons défini un grand nombre de type de tuiles (en particulier pour représenter les murs) pour pouvoir reproduire l'aspect graphique du jeu original. Par ailleurs, certains type de tuiles correspondent à des besoins fonctionnels du jeu qui seront explorés plus tard.

Le labyrinthe `test.txt` correspond à un labyrinthe de test qui sera beaucoup utilisé pour les tests. 

#### Côté modèle : le "Board"

On vous propose une interface `Board` dans le package `model.board`. Le plateau implémentant l'interface contiendra les données du jeu et sera en charge de créer les différents agents (comme PacMan).

Le package contient d'autres classes utilitaires. La seule qui sera utilisée à l'étape 1 est `BoardInitialisationException` qui est l'exception levée en cas d'erreur d’initialisation du plateau.

L'interface vous demande aussi d'implémenter une méthode statique `createBoard` pour créer un plateau. Cette méthode prend en paramètre un élément `GameType` : vous devez implémenter les deux types de plateaux "classique" (le jeu normal utilisant le fichier "maze.txt") et "test" (le jeu test utilisant "test.txt"). 

**Architecture proposée** : les objets de type "Board" vont sauvegarder en interne de nombreuses données relatives à leur fonctionnement. Il est donc judicieux d'écrire deux classes séparées pour le plateau de test et le plateau classique. Cependant, comme une partie du fonctionnement est commun, on pourra les faire hériter d'une même classe abstraite `AbstractBoard`

L'implémentation du plateau et le passage des tests suppose qu'on implémente aussi les acteurs, c'est-à-dire PacMan pour l'étape 1. Cependant, on peut déjà mettre en place une première interface graphique sans PacMan et développer le fonctionnement de PacMan et de l'interface graphique correspondante en parallèle.

La fonction principale à implémenter ici est `initialize` qui doit à la fois charger le labyrinthe et créer les acteurs. On considère ensuite le plateau un peu comme un *système dynamique* (comme dans les TP4 et TP5), c'est-à-dire qu'il fonctionne par évolution successive. La fonction d'évolution est ici `nextFrame` ce qui signifie "prochaine image". L'interface graphique s'occupera de lancer une boucle pour appeler la fonction toutes les 1/60 de secondes. Côté modèle, le plateau doit réaliser l'ensemble des actions nécessaires à chaque nouvelle image, en particulier faire évoluer les acteurs.

#### Côté modèle : PacMan

Les acteurs du plateau peuvent être de 3 types : PacMan, les fantômes, les bonus. Les 3 implémentent l'interface `Actor` définie dans le package `model.actors`. Pour l'étape 1, on ne s'occupe que de PacMan et les autres interfaces définies ici ne sont pas (encore) utiles.

 Un acteur est lié à un plateau et possède une position donnée. Il doit connaître à chaque instant ses coordonnées x,y sur le plateau (en terme de pixels) et la tuile correspondante dans le labyrinthe. 
 
Lors de chaque nouvelle image, l'acteur doit calculer son déplacement et sa nouvelle position. Pour cela, on va utiliser une variable de type `Direction` (la classe est définie dans le package `model`). Dans le cas de PacMan, ça fonctionne de la façon suivante :

* lors de l'initialisation, le plateau doit donner une direction de départ à PacMan (par défaut, c'est à gauche)
* s'il ne reçoit pas de nouvelle "intention", PacMan continue tout droit dans sa direction jusqu'à ce qu'il rencontre un mur et qu'il soit bloqué
* A tout moment, PacMan peut recevoir une intention :
  - si l'intention correspond à la direction inverse de celle qu'il a (`direction.reverse()`), elle est tout de suite appliquée (=la direction de PacMan est modifiée pour celle donnée en intention)
  - si PacMan est bloqué et que la nouvelle intention lui permet de se débloquer, elle est appliquée
  - sinon, l'intention est conservée jusqu'au prochain "milieu de tuile", là on teste que PacMan a le droit de tourner et si c'est le cas, on applique l'intention. Sinon, l'intention est annulée (elle passe à `null`)
  
(remarque : ceci est un comportement simplifié, le vrai comportement de PacMan pourra être implanté en étape 4)
 
 
A chaque nouvelle image, le plateau appelle la méthode `nextFrame` de l'acteur. A l'étape 1, cette méthode se contente d'appeler `nextMove` qui représente le prochain mouvement de l'acteur. La façon de bouger dépendra des acteurs, pour l'étape 1 et dans le cas de PacMan :

* Si PacMan se trouve avant le milieu d'une tuile, il continue d'avancer dans sa direction
* si PacMan est au milieu de la tuile, il vérifie s'il a une "intention" et met à jour sa direction
* si PacMan a dépassé le milieu de la tuile, il vérifie qu'il peut continuer d'avancer dans sa direction. Si ce n'est pas le cas, il arrête d'avancer, il est bloqué

**Architecture proposée :** pour l'instant, seul PacMan implémente l'interface `Actor` mais de nombreux éléments fonctionnels (en particulier sur le contrôle de la position sur le plateau) vont être partagés par l'ensemble des acteurs du plateau. On peut donc dès à présent implémenter une classe `AbstractActor` contenant les éléments communs à tous les acteurs et une classe concrète `PacMan` pour ce qui est spécifique à PacMan

#### Position initiale de PacMan

Dans le plateau classique, PacMan est positionné au départ en x=112, y=211 (Le point (0,0) correspond au coin en haut à gauche du labyrinthe.

Dans le plateau de test, PacMan est positionné en x=35, y=75.

#### Côté contrôleur 

Dans le package `control`, vous trouverez les interfaces et classes nécessaires à l'implantation d'un contrôleur :

* l'interface principale `Controller` : la classe implémentant cette interface doit s'occuper de la création de plateau de jeu (soit dans `initialize`, soit dans `initializeNewGame`), de la vue (dans `initialize`) et permettre de recevoir des actions de la vue.
* `GameAction` est la liste des actions que le contrôleur peut recevoir, soit en cours de jeu (changement de direction pour PacMan), soit dans le déroulement normal de votre application (lancement du jeu, pause, etc.). La liste est assez réduite, vous pouvez tout à fait rajouter des actions si c'est nécessaire
* La classe `ForbiddenActionException` représente une exception qui doit être levée lors d'une action interdite (par exemple, une actions sur PacMan alors que le jeu n'est pas commencé)
* la classe `InterfaceMode`liste des interfaces de jeu possibles. On vous demande d'implémenter le mode `SIMPLE` et le mode `VISUAL`. Le mode simple est un contrôleur *sans* vue. Il permet de tester l'envoie des actions et la réaction du modèle. 

Le contrôleur pourra être créé à l'aide de la méthode statique `getConroller` de l'interface `Controller`. Le rôle de cette méthode est de renvoyer une classe concrète implémentant l'interface en fonction du mode souhaité.

**Architecture proposée** : Implémentez une classe `SimpleController` ainsi qu'une classe `VisualController` qui *hérite* de `SimpleController` en rajoutant les éléments spécifiques à la gestion de la vue. 

#### Côté vue : l'interface `PacManView`

Pour l'interface graphique, nous vous proposons une interface très simple `PacManView` (package `view`) qui sera manipulée par le contrôleur. A vous de créer les différentes classes (héritant de `JFrame` ou `JPanel`) qui permettront d'afficher le jeu. 

A l'étape 1, on vous demande d'implanter 3 `PacManLayout` : 
* `INIT` : le layout initial avant de lancer le jeu
* `GAME_ON` : le layout une fois que le jeu est lancé (qui s'occupera de lancer le timer)
* `PAUSE` : pour mettre le jeu sur pause

Vous êtes très libre de la façon dont vous souhaitez lancer le jeu à l'intérieur de l'application. Une solution simple dans un premier temps est de tout faire par touche du clavier. 

De même, pour l'affichage du labyrinthe et de PacMan, vous pouvez commencer par dessiner de simples carrés (par exemple un carré blanc pour les murs et noir pour les non-murs et un carré jaune pour PacMan).

Enfin, un "pixel" du modèle n'est pas obligé de correspondre à un vrai pixel graphique. Dans mon implémentation, j'ai choisi de représenter chaque pixel par un carré de 2x2 pour que ce soit plus gros.

Le rôle de la vue est aussi d'envoyer les actions au contrôleur, en particulier, l'action `NEXT_FRAME` qui doit être appelée à l'aide d'un timer lorsque le jeu est en cours. On pourra choisir un délai de 17ms qui correspond à 1/60 de secondes, et qui était + ou - la fréquence de rafraîchissement de l'image du jeu original. 

#### Évolution graphique

Une fois que vous avez réalisé l'étape 1. L'évolution du graphisme de votre jeu peut se faire de façon indépendante du reste du développement et donc à son propre rythme. 

Si vous souhaitez vous rapprocher du graphisme original (ce n'est pas obligatoire), nous vous proposons dans le répertoire `resources` des codages en texte pixel par pixel des différents éléments graphiques du jeu, en particulier des tuiles du labyrinthe. Le code 0 représente toujours le noir, les autres chiffres représentent des couleurs différentes en fonction de la pièce dessinée, la taille est aussi variable.

Dans **mon** implantation du jeu : je transforme ces fichiers en tableau de pixels à afficher à l'aide d'une classe spécifique qui prend en paramètre le nom du fichier et une liste de couleurs. Ce traitement se fait à l'initialisation de la vue avant le début du jeu. 

#### Lancement de l'application

L'application sera lancée à partir de la classe `PacManApp`, dans mon implémentation, cela se lance de la façon suivante

    SwingUtilities.invokeLater(() -> {
            try {
                Controller controller = Controller.getController(InterfaceMode.VISUAL);
                controller.setGameType(GameType.CLASSIC);
                controller.initialize();
            } catch (PacManException e) {
                throw new RuntimeException(e);
            }
        });
        
### Étape 2

A l'étape 2, on rajoute plusieurs éléments. L'objectif est d'obtenir une première version du jeu fonctionnelle même si encore très incomplète.

#### Manger les points, compter le score

On commencera par implanter le comptage des cases "à point" dans l'implantation de l'interface `Maze` : globalement, à chaque fois qu'on modifie le labyrinthe, il faudra vérifier si on ajoute / supprime une case contenant un "point" (petit ou gros)

On pourra alors implémenter les nouvelles méthodes de l'étape 2 dans le `Board` : vérifier après chaque évolution si PacMan arrive sur un point, et si c'est le cas le "manger" en mettant à jour le labyrinthe et le score. 

Dans [The Basics](https://pacman.holenet.info/#CH2_The_Basics) on lit que chaque petit point rapporte 10 points de score et que chaque gros point rapporte 50 points. Une fois que le score est implémenté, on peut s'occuper de l'ajouter à l'affichage côté vue.

#### BoardState

A chaque appel de la fonction `nextFrame`, l'état du plateau est susceptible de changer. On va donc implanter une méthode `getBoardState` qui renverra l'état courant et pourra être utilisée par le contrôleur. A l'étape 2, on utilisera :

* `INITIAL` avant le début du jeu
* `STARTED` quand le jeu est cours
* `LEVEL_OVER` quand tous les points ont été mangés
* `LIFE_OVER` quand PacMAn se fait manger par un fantôme

#### Vitesse et stop

Côté `Actor` et en particulier PacMan, on rajoute aussi quelques fonctionnalités. Plutôt que d'avancer d'un pixel à chaque image, les acteurs auront une vitesse (cela s'applique à tous les acteurs et pas seulement pacman, il faut donc que ce soit implanté dans une classe commune comme `AbstractActor`). La vitesse est donné comme un facteur multiplicatif : si la vitesse est $1$, alors l'acteur avance de 1 pixel à chaque image. Si la vitesse est .5, l'acteur n'avance que d'un 1/2 pixel. Seul les pixels entiers sont affichés à l'image. Quand la vitesse est inférieure à 1, PacMan avancera donc simplement plus lentement.

Concrètement, on gardera à présent la position de l'acteur à l'aide d'une valeur de type `double` et la valeur entière sera juste la valeur tronquée (si la position $x$ de PacMan est 1.6, sa position entière est $1$).

Pour l'instant, on n'implante que des vitesses inférieures ou égales à 1 car sinon, cela pose des problèmes d'alignement au moment où l'on arrive au milieu d'une tuile (on pourrait dépasser le milieu sans le voir). D'ailleurs, dans le premier niveau du jeu original, la vitesse "reelle" de PacMan correspond en fait à la valeur 1 mais les vitesses inférieures vont nous être tout de suite utiles pour les fantômes.

Enfin, on rajoute la possibilité pour un acteur d'avoir un "stop time", c'est un nombre d'image surlequel l'acteur n'avance pas. On peut implanter ça en utilisant les méthodes `nextFrame` et `nextMove` : `nextFrame` n’appellera `nextMove` que si le "stop time" est à 0. On prendra soin d'implémenter cette fonctionnalité de façon générique pour tous les acteurs et pas seulement pour PacMan.

Dans [Speed](https://pacman.holenet.info/#CH2_The_Basics), on lit que PacMan s'arrête pour 1 image à chaque fois qu'il mange un point. On utilisera donc cette fonctionnalité au niveau du plateau en plus d'augmenter le score.

**Calcul de la vitesse** : ce [tableau proposé par le PacMan Dossier](https://pacman.holenet.info/#LvlSpecs) donnent toutes les vitesses des différents acteurs du jeu selon le niveau ainsi que l'équivalent en pixels par secondes de la vitesse 100%.

On calcule qu'à 80% (vitesse du niveau 1), PacMan avance donc de 

    75.75757625 * .8 = 60.6
    
pixels par secondes. Ce qu'on arrondit à 1 pixel par 1/60 secondes. 

#### Un premier fantôme

Le gros ajout de l'étape 2 est l'implantation d'un premier fantôme : *Blinky*. Pour cela, il va falloir créer les classes qui implémentent l'interface `Ghost` qui elle-même hérite de `Actor` (on doit donc implémenter toutes les méthodes de `Actor` + les méthodes de `Ghost`).

Dans le jeu final, on aura 4 fantômes avec des comportements différents. Cependant, une grande partie du code sera commune à tous les fantômes.

**Architecture conseillée** : créer une classe `AbstractGhost` qui implémente `Ghost` et hérite de `AbstractActor` puis créer une classe concrète spécifique par fantôme.

Pour comprendre comment fonctionne les fantômes, on vous invite à lire les paragraphes à partir de [Target Tiles](https://pacman.holenet.info/#CH3_Target_Tiles) du dossier PacMan.

L'idée générale est que l'algorithme des fantômes suit le principe d'une "tuile cible" qui ne sera pas la même à différents moments du jeu et en fonction des fantômes. Pour l'instant, on ne s'occupe pas du mode de jeu du fantôme (chase, scatter, etc) qu'on verra à l'étape 3. On va simplement programmer le système de cible et le mouvement du fantôme en conséquence.

A tout moment du jeu, un fantôme est en mouvement suivant une *direction* et possède aussi une *intention* qui est sa prochaine direction. Quand il rejoint le centre d'une tuile :

* il applique son intention et met donc à jour sa direction
* il calcule sa nouvelle intention. Pour cela, il regarde où il peut aller à partir de la **prochaine tuile** sachant qu'il n'a pas le droit de revenir en arrière. Il choisit la tuile possible qui le rapproche le plus de sa tuile cible (selon la distance euclidienne)

Ce mécanisme est le même pour tous les fantômes : seul la tuile cible change. La méthode `nextMove` pourra donc être partagée par tous les fantômes mais la méthode `getTarget` sera spécifique à chaque fantôme.

A l'étape 2, on se contente de programmer `Blinky` : la cible de Blinky est toujours la tuile sur laquelle se trouve PacMan.

#### Un fantôme sur le plateau

Les fantômes existent toujours par rapport à un plateau (`Board`) sur lequel ils se situent. On devra donc implémenter les méthodes du plateau qui permettent de récupérer les fantômes (sous forme de liste ou un par un). Si un fantôme n'est pas implanté ou n'existe pas, on pourra renvoyer `null` (par exemple, le plateau de test ne contient pas les fantômes). De même, le plateau de test renverra une liste vide pour la liste des fantômes.

Enfin, on va avoir besoin de détecter quand un fantôme attrape PacMan (quand ils se trouvent sur la même tuile). Cette détection se fera à chaque appel de `nextFrame` après que les différents acteurs aient fait leurs actions. En cas de collision, l'état du plateau sera passé à `LIFE_OVER`

Le plateau doit aussi s'occuper de mettre à jour la vitesse des fantômes quand ils passent sur des tuiles "lentes" (de type SL), dans ce cas, ils ont une vitesse de 0.5.

#### Position initiale de BLinky

La position initiale de Blinky est x=112, y=115. Sa direction de départ sera vers la gauche et sa vitesse est .94.

#### Vue et contrôleur

Du côté du contrôleur, il va falloir détecter après chaque action `NEXT_FRAME` si l'état du plateau a changé et envoyé l'information à la vue.

Côté vue, on implémentera les deux layouts `LEVEL_OVER` et `LIFE_OVER` : dans les deux cas, il faudra arrêter le timer du jeu, éventuellement afficher un message, et surtout attendre une action de l'utilisateur / utilisatrice pour relancer le jeu avec les actions `NEXT_LEVEL` et `NEW_GAME` (pour l'instant, on recommence tout à chaque fois)

### Étape 3 

On va maintenant compléter notre game play pour obtenir un "vrai" jeu de PacMan.

#### Les niveaux et les vies

Le plateau doit maintenant enregistrer à quel niveau on se trouve et implémenter une méthode `initializeNewLevel` qui permet de réinitialiser le plateau à un niveau donné. Cette méthode sera appelée par le contrôleur après une action `NEXT_LEVEL`, elle doit recharger le labyrinthe (le score est bien évidemment conservé)

De même, on va maintenant compter le nombre de vies restantes à PacMan (qui commence avec 2 vies supplémentaires). Quand PacMan se fait manger par un fantôme, on séparera l'état `LIFE_OVER` de `GAME_OVER` en fonction de s'il reste des vies. On implémente aussi une méthode `initiliazeNewLife` qui replace PacMan et les fantômes en position initiale mais ne recharge pas le labyrinthe (les points mangés restent mangés, le score reste le même).

#### Scatter and Chase

Les fantômes ont deux modes de jeu qui varient au fur et à mesure du jeu : Scatter et Chase. En mode Scatter, la tuile cible est modifiée et les fantômes vont dans les coins du labyrinthe. On trouve des explications dans la section [Scatter, Chase, Repeat](https://pacman.holenet.info/#CH2_Scatter_Chase_Repeat) avec le temps passé dans chaque mode. Le temps est exprimé en secondes. On pourra le convertir en nombre d'images (en multipliant par 60), ce sera ensuite au plateau de retenir dans quel mode sont les fantomes et de calculer quand le mode doit être changé.

Chaque fantôme retient dans quel mode de jeu il évolue et on peut contrôler son mode de jeu avec `setGhostState` et `getGhostState`. On utilisera aussi la méthode `changeGhostState` qui permet de changer l'intention du fantôme quand il passe de Sctater à Chase et vice-versa (lire [Reversal of fortune](https://pacman.holenet.info/#CH2_Reversal_of_Fortune))

Pour des raisons de tests, on permet aussi de désactiver le comptage du temps et changement de mode des fantomes avec `disableStateTime`.

#### Frightened

De même, on ajoute les modes `FRIGHTENED` et `FRIGHTENED_END` qui correspondent aux fantômes "effrayés" quand PacMan a mangé un "gros point". Le comportement des fantômes est décrit dans [Frightening behavior](https://pacman.holenet.info/#CH2_Frightening_Behavior) : leur nouvelle intention est alors aléatoire (mais ne peut jamais retourner en arrière). 

On utilise le mode `FRIGHTENED_END` pour le moment où les fantômes "clignotent". Dans [cette table](https://pacman.holenet.info/#LvlSpecs) on trouve le temps du mode frightened en fonction du niveau et le nombre de flash. On considère qu'un flash noir / blanc dure 30 ms (15ms en blanc, 15 ms en couleur normale)

#### Tous les fantomes !

On implémente l'ensemble des fantômes avec leurs spécificités principalement données par leurs différentes cibles. Les explications sur chacun des fantômes sont disponibles dans la section [Meet the Ghost](https://pacman.holenet.info/#Chapter_4). 

Pour des raisons de tests, on permet aussi de désactiver certains fantômes (qui ne seront donc pas créés) avec `disableGhost` (cette fonction sera toujours appelée avant l'initialisation du plateau).

Les positions initiales des fantômes sont les suivantes :

* Pinky : 112, 139
* Inky : 96, 139
* Clyde : 128, 139

(Remarque : on ne s'occupe pas pour l'instant du mode "Elroy")

#### Le Ghost Pen

Avec l'arrivée des autres fantômes, arrivent aussi la question du "ghost pen", ou la maison des fantômes. En effet, seul Blinky commence en dehors de cette "maison".

Chaque fantôme retient quel est son état actuel par rapport à la maison avec son `ghostPenState` :

* `OUT` : le fantôme est dehors
* `IN` : le fantôme est dedans, il attend
* `GET_OUT` : le fantôme est en train de sortir
* `GET_IN` : le fantôme est en train d'enter

La gestion de qui sort quand est un peu compliquée et se fait avec ce qu'on appelle des "compteurs". On trouve l'explication dans la section [Home Sweet Home](https://pacman.holenet.info/#CH2_Home_Sweet_Home). Dans un premier temps, vous pouvez ignorer cette mécanique et vous occuper simplement de faire sortir les fantômes dès qu'ils sont à l'intérieur de la maison.

#### Manger des fantômes

Quand on est en mode `FRIGHTENED` ou `FRIGHTENED_END`, si PacMan arrive sur la tuile d'un fantôme celui-ci est "mangé" par PacMan et il passe en mode `DEAD`. Les points gagnés sont expliqués dans [The Basics](https://pacman.holenet.info/#CH2_The_Basics). 

#### Entrer et sortir du Ghost Pen

Lorsqu'un fantôme est `DEAD`, sa tuile cible est celle renvoyée par `penEntry`, soit la tuile en ligne 14 et colonne 13 pour le mode classique.

Lorsqu'il atteint les coordonnées données par `outPenXPosition` et `outPenYPosition` (soient x=112 et y=115), son "ghost pen mode" passe de OUT à GET_IN et il entre dans le ghost pen pour rejoindre sa position intérieure.

A l'intérieur du Ghost pen, on ne s'occupe plus des tuiles et du fonctionnement habituel des déplacements (avec le calcul de l'intention et de la direction), l'objectif est simplement de placer le fantôme dans ses coordonnées déterminées par les méthodes `penGhostXPosition` et `penGhostYPosition` de `Board`. Dans le cas classique, on a :

* pour Blinky : 112, 139
* pour Pinky : 112, 139
* pour Inky : 96, 139
* pour Clyde : 128, 139

Quand ils atteignent leur position, les fantômes passent en mode `IN`. Dans le jeu original, ils avancent alors de haut en bas. Si on veut reproduire se mouvement (optionnel), on pourra utiliser les méthodes `minYPen` et `maxYPen` en faisant bouger les fantômes de 5 pixels vers le haut ou vers le bas par rapport à leur position de départ.

Quand la décision de faire sortir les fantôme est prise par le plateau, le fantôme passe en mode `GET_OUT` et doit rejoindre les coordonnées données par `outPenXPosition` et `outPenYPosition`. Dès qu'ils sont à cette position, ils passent en mode `OUT` et prennent la direction que leur méthode `getOutOfPenDirection` renvoie et reprenne un comportement normal.

Par ailleurs, leur vitesse pour toutes ces actions sera leur vitesse "lente", ou "tunnel" (.5 au niveau 1)

#### Compteurs : quand sortir du Ghost Pen

La décision de qui sort du Ghost Pen est prise par le plateau en fonction de différents "compteurs". On trouve les explications dans la section [Home Sweet Home](https://pacman.holenet.info/#CH2_Home_Sweet_Home). On vous fournit une classe `Counter` dans le package `model.board` que vous pouvez utiliser pour compter à la fois les points mangés par PacMan (soit dans les compteurs des fantômes, soit dans le `specialDotCounter`) ou le nombre d'images où PacMan n'a rien mangé (le `noDotCounter`).

#### Vitesse rapide et valeurs des vitesse

Dès cette étape, vous pouvez implanter les "vitesses rapides", c'est-à-dire supérieure à 1. Pour cela, il faut faire attention :
* à quand les acteurs atteignent le "milieu" des tuiles, car ils peuvent très bien "sauter" la valeur exacte du milieu 
* à renormaliser les valeurs de x ou y quand les acteurs tournent (quand un acteur avance horizontalement, il doit être placé au milieu de la tuile verticalement et vice versa)

On vous propose des tests pour vérifier que la vitesse rapide fonctionne.

Dans tous les cas, vous pouvez implanter les "valeurs de vitesse" telles que calculées à partir [de ce tableau](https://pacman.holenet.info/#LvlSpecs). On distingue plusieurs vitesses :

* la vitesse de PacMan en mode normal : 1 pour le niveau 1, 1.14 pour les niveaux 2--4, 1.26 pour les niveaux 5--20 et 1.14 pour les niveaux > 20
* la vitesse de PacMan en mode frightened : 1.14 pour le niveau 1, 1.2 pour les niveaux 2--4, 1.26 sinon
* la vitesse normale des fantômes : .94 pour le niveau 1, 1.07 pour les niveaux 2--4, 1.2 sinon
* la vitesse "tunnel" des fantômes (sur les tuiles lentes ou à l'intérieur du ghost pen) : .5 pour le niveau 1, .57 pour les niveaux 2--4, .63 sinon
* la vitesse "frightened" des fantômes : .63 pour le niveau 1, .69 pour les niveaux 2--4, .75 sinon
* la vitesse des fantômes morts : 1.26 

Note : si un fantôme est en mode "frightened" et passe dans le tunnel, il prend la vitesse tunnel.

Conseil : après chaque image, calculer la valeur courante de la vitesse et mettre à jour les fantômes

Si vous n'avez pas implanté les vitesses rapides, implantez quand même les fonctions de valeurs de vitesse en renvoyant 1 comme vitesse maximale. Ainsi, tous les autres tests passeront.

### Étape 4

On y est presque ! On a cette fois un jeu fonctionnel et no va maintenant rajouter les petits détails qui nous permettront de coller au jeu original.

Les différents éléments restants sont d'ailleurs indépendants les uns des autres. Vous pouvez en faire certains et pas d'autres.

#### La vie supplémentaire

Dans le jeu classique, on gagne une vie supplémentaire quand on atteint 10000 points. L'interface `Board` permet de spécifier la valeur où cette vie est gagnée et rajoute donc la vie au moment adéquat.

#### Les Bonus

On implémente le système de bonus du jeu original. Les bonus sont des éléments qui peuvent être mangés par PacMan pour gagner des points. Les types de Bonus avec les points gagnés sont dans `BonusType`.

Le Bonus en tant que tel est considéré comme un acteur et implémente l'interface `Bonus` du package `model.actors`. Le fonctionnement des bonus est expliqué dans [The Basics](https://pacman.holenet.info/#CH2_The_Basics). Côté implémentation, on place le bonus le moment voulu en position 112, 163 et le bonus est par défaut actif. Le bonus reste actif un certain temps, qu'on peut contrôler par exemple avec une implantation particulière de `nextMove` et le `stopTime` des acteurs. Les bonus sont des acteurs immobiles, on les déplace donc pas.

Le bonus disparait soit si PacMan le mange soit s'il devient inactif : dans les deux cas, c'est le plateau qui s'en occupe.

#### Elroy

Le mode "Elroy" est expliqué dans la section relative à [Blinky](https://pacman.holenet.info/#CH4_Blinky) : les interfaces `Board` et `Ghost` prévoient plusieurs méthodes pour gérer ce mode. Par défaut, on considère que la valeur "Elroy" de tous les fantômes est 0 (c'est-à-dire : mode normal). Quand c'est nécessaire, on passera Blinky en mode 1 ou 2.

Les valeurs de vitesse sont les suivantes

Pour Elroy 1 :

* niveau 1 : 1
* niveau 2--4 : 1.14
* autres niveaux : 1.26

Pour Elroy 2 :

* niveau 1 : 1.14
* niveaux 2--4 : 1.2
* autres niveaux : 1.33

#### Évènements 

On propose d'implanter un système "d'évènements" pouvant arriver sur le plateau à chaque tour de jeu. Ces évènements peuvent être récupérés par le contrôleur et la vue pour afficher des réactions (animation, son, etc). On vous propose une liste d'évènements possibles : vous pouvez bien sûr la compléter !

#### Cornering

La façon dont on a implanté les "tournants" de PacMan ne correspond pas à ce qui se passe dans le vrai jeu. PacMan a en effet la capacité de faire du [Cornering](https://pacman.holenet.info/#CH2_Cornering) comme expliqué dans le lien. Nous vous proposons une série de tests spécifiques au cornering dans `PacManCorneringTurnTest`. Les tests de tournants "normaux" sont conservés dans `PacManClassicTurnTest`. Les tests doivent passer dans l'une des deux classes en fonction de ce que vous avez implanté (mais ne peuvent pas passer dans les deux en même temps !)

### Aller plus loin ?

Vous avez fait tout ça ? Tout fonctionne ? Les tests de l'étape 4 passsent ? **Bravo !!**

Si vous avez fini, vous pouvez faire plus : créer de nouveaux modes de jeu, de nouvelles options, etc. Laissez faire votre imagination.






