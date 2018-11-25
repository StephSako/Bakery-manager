
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
<<<<<<< HEAD

Optimiser les actions de l'utilisateur
=
Afin d'optimiser les actions de l'utilisateur, nous avons décidé d'utiliser une classe **Opération** listant chacune des actions réalisables sur la caisse enregistreuse comme afficher les notes en cours de paiement, le stock du restaurant ou encore créer la **Note** d'un client. Nous avons ensuite stocker toutes ces méthodes dans une HashMap : ainsi, chaque fonctionnalité est rattachée à une 'lettre' saisie par l'utilisateur ;

`private final Map<String, Runnable> commands = new HashMap<>();
commands.put("b", () -> operation.casB(logger, stockRestaurant));`
=======

Optimiser les actions de l'utilisateur
=
Afin d'optimiser les actions de l'utilisateur, nous avons décidé d'utiliser une classe **Opération** listant chacune des actions réalisables sur la caisse enregistreuse comme afficher les notes en cours de paiement, le stock du restaurant ou encore créer la **Note** d'un client. Nous avons ensuite stocker toutes ces méthodes dans une HashMap.

`private final Map<String, Runnable> commands = new HashMap<>();
commands.put("b", () -> operation.casB(logger, stockRestaurant));`

Ainsi, chaque fonctionnalité est rattachée à une 'lettre' saisie par l'utilisateur.
`commands.get(lettre).run();`
>>>>>>> 4d71f29e87270482594915c41f28a94c912338ed
