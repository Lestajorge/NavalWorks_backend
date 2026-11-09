package F110.model;

/**
 * Estados por los que puede pasar un isométrico durante su gestión.
 */
public enum EstadoIsometrico {
    /** El documento todavía está en preparación. */
    BORRADOR,

    /** El documento está publicado para su uso. */
    PUBLICADO,

    /** El documento ha sido retirado y archivado. */
    ARCHIVADO
}
