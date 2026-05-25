public class FacturacionLegacy {
    private static final int    TIPO_CLIENTE_PREMIUM   = 1;
    private static final int    TIPO_CLIENTE_ESTANDAR  = 2;
    private static final double DESCUENTO_SOCIO_VIP    = 0.25;
    private static final double DESCUENTO_PREMIUM      = 0.15;
    private static final double DESCUENTO_ESTANDAR     = 0.05;

    /**
     * Calcula el importe final de una transacción aplicando el descuento
     * correspondiente según el tipo de cliente y si es socio VIP.
     *
     * @param importeBase  Importe bruto de la transacción. Debe ser mayor que 0.
     * @param tipoCliente  Tipo de cliente: 1 para Premium, 2 para Estándar.
     * @param esSocioVip   Indica si el cliente tiene la condición de socio VIP.
     * @return Importe final con el descuento aplicado, o 0 si el importe no es válido.
     */
    public double calcularTotal(double importeBase, int tipoCliente, boolean esSocioVip) {
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

    /**
     * Método legado mantenido por compatibilidad con los tests existentes.
     * Delega toda la lógica en {@link #calcularTotal(double, int, boolean)}.
     *
     * @param importeBase  Importe bruto de la transacción.
     * @param tipoCliente  Tipo de cliente: 1 para Premium, 2 para Estándar.
     * @param esSocioVip   Indica si el cliente tiene la condición de socio VIP.
     * @return Importe final con el descuento aplicado, o 0 si el importe no es válido.
     * @deprecated Usar {@link #calcularTotal(double, int, boolean)} en su lugar.
     */
    @Deprecated
    public double cT(double importeBase, int tipoCliente, boolean esSocioVip) {
        return calcularTotal(importeBase, tipoCliente, esSocioVip);
    }
}
