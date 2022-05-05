# Publication d'une nouvelle release

Cette doc a pour but de présenter comment publier une nouvelle release 
du projet Statique grâce à Github action.

## Modifier le changelog

Pour pouvoir customiser le text affiché dans le changelog, il est conseillé
de modifier le fichier `CHANGELOG.md` dans le dossier `doc` afin d'y ajouter le texte
à afficher dans la release

## Publier une release

Une fois la branche `main` mise à jour et le changelog complété, il est possible de publier 
une nouvelle release. Pour ce faire, il suffit d'utiliser la commande
suivante
    
``` 
git tag -a v0.0.X -m "Release v0.0.X" 
git push origin --tags
```

## Supprimer un tag 

Supprimer un tag local :
``` git tag -d v0.0.X ```

Supprimer un tag distant :
``` git push --delete origin v0.0.X ```

