# Générateur de site statique en Java

![example workflow](https://github.com/dil-classroom/projet-louis_mondaini_nunez/.github/workflows/github-actions-maven.yml/badge.svg)

## Table des matières

* [Description du projet](#description-du-projet)
  * [But](#but)
  * [Auteurs](#auteurs)
  * [Licence](#licence)
  * [Contributions](#contributions)


## Description du projet

### But

Le but de ce projet est de créer un générateur de site statique en Java. Il est développé dans le cadre du cours **Processus de développement en ingénierie logicielle (DIL)** enseigné à l'HEIG. Ce projet est également destiné à expérimenter et appliquer différents processus de développement.

### Auteurs

* Louis Hadrien
* Mondaini Damiano
* Nunez Tania

## Installation

Pour pouvoir utiliser le programme `statique`, il suffit de lancer le build du 
projet puis de unzip la source en exécutant les commandes suivantes

```
mvn clean install \
    && unzip -o target/statique.zip
```

Il est alors ensuite possible d'ajouter le projet dans son PATH

Linux : 
```export PATH=$PATH:`pwd`/statique/bin```

Windows : utiliser git bash

## Licence

Ce projet est sous licence **MIT**. Cela signifie que toute personne peut le reprendre, le modifier et le redistribuer.

## Contributions

Pour contribuer au projet, merci de consulter le code de [conduite du projet](code-of-conduct.md).
