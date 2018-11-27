
# ProjetPoo3A

Structure globale du projet
=
```
.
+-- logger/
|  +-- src/
|  |   +-- logger/
|  |       +-- (*.java)
|  +-- bin/
+-- testframework/
|  +-- src/
|  |   +-- test/
|  |       +-- (*.java)
|  +-- bin/
+-- restaurant/
|  +-- src/
|  |   +-- restaurant/
|  |       +-- (*.java)
|  +-- bin/
+-- compile.sh
+-- test.sh
+-- launch.sh
+-- README.md
```

Gérer les stock des produits du restaurant
=
Afin de gérer le fait que les **Produits** contenus dans le **Stock** du **Restaurant** puissent avoir une quantité finie (*tels que les Smoothies, Bagel, etc ...* ou infinie (*tels que les cafés*), nous avons choisi de créer une classe abstraite **Produit** ayant deux constructeurs, l'un prenant en paramètre le stock du produit, l'autre n'en prenant pas.

Optimiser les actions de l'utilisateur
=
Afin d'optimiser les actions de l'utilisateur, nous avons décidé d'utiliser une classe **Opération** listant chacune des actions réalisables sur la caisse enregistreuse comme afficher les notes en cours de paiement, le stock du restaurant ou encore créer la **Note** d'un client. Nous avons ensuite stocker toutes ces méthodes dans une HashMap.

`private final Map<String, Runnable> commands = new HashMap<>();
commands.put("b", () -> operation.casB(logger, stockRestaurant));`

Ainsi, chaque fonctionnalité est rattachée à une 'lettre' saisie par l'utilisateur.
`commands.get(lettre).run();`

Utilisation de JUnit
=
Nous avons choisi de développer notre framework de test avec l'API Java JUnit. Pour ce faire, nous avons dû ajouter un répertoire lib/ contenant l'API JUnit, qui sera ensuite appelé dans le script test.sh pour compiler les fichiers .java.
