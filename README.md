# Left Factoring
_JavaFx GUI Application_

Graphical User Interface Based JavaFX Program for computing Left Factoring. Left Factoring is a grammar transformation technique. It consists in "factoring out" prefixes which are common to two or more productions.

For example, going from:

```
A → α β | α γ
```

to:
```
A → α A'

A' → β | γ
```
