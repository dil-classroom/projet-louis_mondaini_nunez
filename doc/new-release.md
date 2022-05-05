# Publication d'une nouvelle release

Cette doc a pour but de présenter comment publier une nouvelle release 
grâce à Github action.

## Publier une release

Une fois la branche `main` mise à jour, il est possible de publier 
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

