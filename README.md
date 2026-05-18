# Refactorización de Código Legado — "Limpieza de Primavera"

## Descripción

El objetivo de este proyecto era tomar una clase del sistema de (`FacturacionLegacy.java`) con alta deuda técnica y refactorizarla  sin cambiar su comportamiento, aplicando buenas prácticas de desarrollo.

---

## El problema: código original

El método original `cT` presentaba tres problemas graves:

**1. Variables sin significado**
Los nombres `m`, `tC`, `dV` y `cT` no aportaban ninguna información sobre lo que representaban, obligando a cualquier desarrollador a adivinar su propósito.

**2. Números mágicos**
Los valores `0.25`, `0.15` y `0.05` estaban hardcodeados directamente en el código sin ningún contexto. Si los descuentos cambiaran, habría que buscarlos por todo el código.

**3. Código spaghetti**
La lógica estaba construida con `if-else` anidados en forma de flecha, lo que hacía muy difícil seguir el flujo de ejecución.

```java
// ANTES — código original con deuda técnica
public double cT(double m, int tC, boolean dV) {
    if (m > 0) {
        if (tC == 1) {
            if (dV == true) return m - (m * 0.25);
            else return m - (m * 0.15);
        } else {
            if (tC == 2) {
                return m - (m * 0.05);
            } else {
                return m;
            }
        }
    } else {
        return 0;
    }
}
```

---

## La solución: cambios realizados

### 1. Extracción de constantes
Hemos sustituidos los números mágicos por constantes `private static final` con nombres en SNAKE_CASE:

```java
private static final int    TIPO_CLIENTE_PREMIUM  = 1;
private static final int    TIPO_CLIENTE_ESTANDAR = 2;
private static final double DESCUENTO_SOCIO_VIP   = 0.25;
private static final double DESCUENTO_PREMIUM     = 0.15;
private static final double DESCUENTO_ESTANDAR    = 0.05;
```

### 2. Renombrado de parámetros
Hemos cambiado el nombre a los parámetros del método para que expresen claramente su significado:

| Antes | Después |
|-------|---------|
| `m` | `importeBase` |
| `tC` | `tipoCliente` |
| `dV` | `esSocioVip` |

### 3. Cláusulas de guarda (Guard Clauses)
Se eliminaron todos los bloques `else` invirtiendo las condiciones y usando retornos tempranos. El código ahora fluye de forma vertical y es fácil de leer:

```java
// DESPUÉS — código refactorizado
public double cT(double importeBase, int tipoCliente, boolean esSocioVip) {
    if (importeBase <= 0) return 0;

    if (tipoCliente == TIPO_CLIENTE_PREMIUM && esSocioVip) {
        return importeBase - (importeBase * DESCUENTO_SOCIO_VIP);
    }

    if (tipoCliente == TIPO_CLIENTE_PREMIUM) {
        return importeBase - (importeBase * DESCUENTO_PREMIUM);
    }

    if (tipoCliente == TIPO_CLIENTE_ESTANDAR) {
        return importeBase - (importeBase * DESCUENTO_ESTANDAR);
    }

    return importeBase;
}
```

### 4. Documentación JavaDoc
Se añadió documentación estándar JavaDoc en la cabecera del método explicando su propósito, cada parámetro y el valor de retorno.

---

## Tests unitarios

La clase `FacturacionLegacyTest.java` contiene la batería de pruebas que actúa como red de seguridad. Cubre los siguientes casos:

| Test | Entrada | Resultado esperado |
|------|---------|-------------------|
| Cliente Premium + VIP | importe=100, tipo=1, vip=true | 75.0 (25% descuento) |
| Cliente Premium sin VIP | importe=100, tipo=1, vip=false | 85.0 (15% descuento) |
| Cliente Estándar | importe=100, tipo=2, vip=false | 95.0 (5% descuento) |
| Cliente sin categoría | importe=100, tipo=3, vip=false | 100.0 (sin descuento) |
| Importe negativo | importe=-50, tipo=1, vip=true | 0.0 |
| Importe cero | importe=0, tipo=2, vip=false | 0.0 |

