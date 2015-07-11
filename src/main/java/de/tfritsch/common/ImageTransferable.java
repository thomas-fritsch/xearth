package de.tfritsch.common;

import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

/**
 * A <code>Transferable</code> which implements the capability required to
 * transfer an {@link Image} object. This <code>Transferable</code> properly
 * supports {@link DataFlavor#imageFlavor}.
 * @author Thomas Fritsch
 */
public class ImageTransferable implements Transferable {

    private Image image;

    /**
     * Creates a <code>Transferable</code> capable of transferring the specified
     * <code>Image</code>.
     * @param image
     *            the <code>Image</code> carried by this
     *            <code>Transferable</code>
     */
    public ImageTransferable(final Image image) {
        if (image == null) {
            throw new NullPointerException("image");
        }
        this.image = image;
    }

    @Override
    public final DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[] {DataFlavor.imageFlavor};
    }

    @Override
    public final boolean isDataFlavorSupported(final DataFlavor flavor) {
        return DataFlavor.imageFlavor.equals(flavor);
    }

    @Override
    public final Object getTransferData(final DataFlavor flavor)
            throws UnsupportedFlavorException {
        if (!isDataFlavorSupported(flavor)) {
            throw new UnsupportedFlavorException(flavor);
        }
        return image;
    }
}
