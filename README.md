# Générateur de site statique en Java

![example workflow](https://github.com/dil-classroom/projet-louis_mondaini_nunez/actions/workflows/github-actions-maven.yml/badge.svg)
## Table des matières

* [Description du projet](#description-du-projet)
  * [But](#but)
  * [Auteurs](#auteurs)
* [Documentation](#documentation)
* [Manuel d'utilisation](#manuel-dutilisation)
* [Licence](#licence)
* [Contributions](#contributions)


## Description du projet

### But

Le but de ce projet est de créer un générateur de site statique en Java. Il est développé dans le cadre du cours **Processus de développement en ingénierie logicielle (DIL)** enseigné à l'HEIG. Ce projet est également destiné à expérimenter et appliquer différents processus de développement.

### Auteurs

* Louis Hadrien
* Mondaini Damiano
* Nunez Tania

### Installation

Récupérer la dernière [release](https://github.com/dil-classroom/projet-louis_mondaini_nunez/releases) du projet (actuellement il s'agit de la version v0.0.3).
Pour pouvoir utiliser le programme `statique`, il suffit de lancer le build du 
projet puis de unzip la source en exécutant les commandes suivantes

```
mvn clean install \
    && unzip -o target/statique.zip
```
![mvn clean install result](/use-examples/mvn_clean_install_result.JPG)

![files unzipped](/use-examples/unzip_result.JPG)

Il est alors ensuite possible d'ajouter le projet dans son PATH

Linux : 
```export PATH=$PATH:`pwd`/statique/bin```

![export path](/use-examples/export_path_result.JPG)

Windows : utiliser git bash ou directement modifier la variable d'environnement *PATH*

Une fois le projet installé et configuré, afin de tester son fonctionnement, il est possible d'exécuter la commande ``statique --version`` qui devrait présenter le comportement suivant si l'installation et la configuration ont été un succès:

![statique version result](/use-examples/statique_version.JPG)

## Documentation

Le générateur de site statique propose les commandes suivantes :

### ``init``

La commande ``init`` permet de créer un projet statique et ainsi d'initialiser le projet dans un dossier de votre choix.
Pour utiliser cette commande, il suffit de passer en paramètre le chemin absolu du dossier dans lequel vous 
souhaitez créer le projet. A la suite de cette commande, le projet sera créé et une structure de dossiers / fichiers
de base sera créé et pourra ainsi être complétée par l'utilisateur.

Les dossiers / fichiers de base sont créés dans le sous-dossier ``site`` dans lequel vous trouverez 
les fichiers de configuration du générateur de site statique. Le fichier de configuration de base se nomme 
``config.yaml``. Un autre fichier de démo ``index.md`` est créé dans le même dossier.

La structure des fichiers créés est la suivante :

```
site/
    template/
    config.yaml
    index.md
```

Exemple d'utilisation sous Linux :

```
statique init /home/user/projet
```

Exemple d'utilisation sous Windows :

![statique init example](/use-examples/statique_init.JPG)

### ``build``

La commande ``build`` permet de générer le site statique et ainsi le compiler. Cette commande
va avoir comme effet de compiler tous le fichiers du projet en ``html``. Le résultat de
cette commande se trouve dans le sous-dossier ``build`` du projet. Pour lancer cette commande, 
il suffit de passer en paramètre le chemin absolu du dossier dans lequel se trouve le projet.

A la suite de cette commande, la structure du projet sera :

```
site/
    build/
      index.html
    template/
    config.yaml
    index.md
```

Il est également possible de passer en paramètre de cette commande
l'option ``--watch``. Cette option va avoir pour effet de compiler le projet à chaque modification
de fichier dans le projet.

Exemples d'utilisation :

```
statique build /home/user/projet [--watch]
```
![statique build example](/use-examples/statique_build.JPG)

![statique build watch example](/use-examples/statique_build_watch.JPG)

![statique build watch modified example](/use-examples/statique_build_watch_modif.JPG)

### ``serve``

La commande ``serve`` permet de lancer un serveur web sur le port ``8085`` et ainsi permettre de
consulter le site statique. Pour lancer cette commande, il suffit de passer en paramètre le chemin
absolu du dossier dans lequel se trouve le projet. 

Il est également possible de passer en paramètre de cette commande
l'option ``--watch``. Cette option va avoir pour effet de compiler le projet à chaque modification
de fichier dans le projet. Le modification de cette recompilation sera instantanément
visible sur le serveur web.

Exemple d'utilisation :

```
statique serve /home/user/projet [--watch]
```

![statique serve example](/use-examples/statique_serve.JPG)

### ``clean``

La commande ``clean`` permet de supprimer le dossier ``build`` du projet. Pour lancer cette commande,
il suffit de passer en paramètre le chemin absolu du dossier dans lequel se trouve le projet.

Exemple d'utilisation :

```
statique clean /home/user/projet
```

![statique clean example before](/use-examples/statique_clean_before.JPG)

![statique clean after](/use-examples/statique_clean_after.JPG)

## Templating

Dans le contenu des divers fichiers de template présents dans le dossier `template/` du projet, il est possible d'utiliser 
le language de templating `handlebars`. Le fichier `layout.html` est le template principal du site. 
Toutes les pages du site seront générées en utilisant ce template.

Si vous souhaitez changer ce template et en ajouter d'autres, voici les principales commandes `handlebars`

- `{{ site.var }}` - Permet d'insérer une variable de site dans le template. Les 
variables de site sont définies dans le fichier `config.yaml` du projet.
- `{{ page.title }}` - Permet d'insérer le titre de la page dans le template.
Les variables de page sont définies dans chaque fichier de page mardown
- `{{ > fichier }}` - Permet d'insérer le fichier fichier.html dans le template.


## Manuel d'utilisation

Maintenant que les différentes commandes ont été décrites, voici un petit manuel d'utilisation.
Cet exemple a pour objectif de montrer comment utiliser le générateur de site statique en partant
d'un dossier vide.

1. Créer un projet statique

```
statique init /home/user/projet
```

2. Modifier le fichier ``config.yaml`` contenant la configuration du générateur de site statique

3. Modifier le fichier ``index.md`` contenant le contenu du site statique et y ajouter
le contenu souhaité. Il est également possible de créer d'autres fichiers ou sous-dossier de le même
répertoire. Tous les fichiers et sous-dossier seront automatiquement compiler lors de la commande ``build``.

4. Ajouter un fichier de template dans le dossier ``template``. Les fichiers de templates sont des fichiers
``.html``. Ces fichiers contiennent une structure ``html``. Dans ces 
fichiers, il est possible d'utiliser des variables définies dans le fichier de configuration du générateur.
De plus, il est possible d'inclure au moyen de ``{% include fichier.html }`` d'autres 
fichiers de template.

5. Compiler le projet (en utilisant ou non l'option ``--watch``)

```
statique build /home/user/projet
```

6. Lancer le serveur web (en utilisant ou non l'option ``--watch``)

```
statique serve /home/user/projet
```

Une fois le serveur web lancé, il est possible de naviguer sur le site statique en utilisant
le navigateur web et en tapant l'adresse suivante : ``http://localhost:8085/``.

7. Supprimer le dossier ``build`` du projet. Pour cela, il suffit de lancer la commande :

```
statique clean /home/user/projet
```


## Licence

Ce projet est sous licence **MIT**. Cela signifie que toute personne peut le reprendre, le modifier et le redistribuer.

## Contributions

Pour contribuer au projet, merci de consulter le code de [conduite du projet](code-of-conduct.md).



