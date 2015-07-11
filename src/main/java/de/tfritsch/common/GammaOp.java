package de.tfritsch.common;

import java.awt.image.ByteLookupTable;
import java.awt.image.LookupOp;

/**
 * Gamma correction for BufferedImages. This class allows the Source to be the
 * same as the Destination.
 * @author Thomas Fritsch
 * @see <a href="http://en.wikipedia.org/wiki/Gamma_correction">Wikipedia: Gamma
 *      correction</a>
 */
public class GammaOp extends LookupOp {

    /**
     * Constructs a GammaOp filter.
     * @param gamma
     *            A value less than 1 will enhance contrast of dark parts and
     *            reduce contrast of bright parts. A value greater than 1 will
     *            do the opposite.
     */
    public GammaOp(final double gamma) {
        super(createLookupTable(gamma), null);
    }

    private static final int UBYTE_MAX = 0xff;

    private static ByteLookupTable createLookupTable(final double gamma) {
        if (gamma <= 0) {
            throw new IllegalArgumentException("gamma is not positive");
        }
        byte[] array = new byte[UBYTE_MAX + 1];
        for (int i = 0; i <= UBYTE_MAX; i++) {
            array[i] = (byte) (UBYTE_MAX * Math.pow((double) i / UBYTE_MAX,
                    gamma));
        }
        return new ByteLookupTable(0, array);
    }

}
