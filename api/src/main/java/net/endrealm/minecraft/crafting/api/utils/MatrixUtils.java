package net.endrealm.minecraft.crafting.api.utils;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class MatrixUtils {
    public static <T> T[][] cutMinimal(T[][] shape, MatrixSupplier<T> supplier) {

        boolean preCheck = true;
        int preRowCount = 0;
        int postRowCount = 0;

        // Check for empty rows
        for (int y = 0; y < shape.length; y++) {
            var empties = 0;
            for (int x = 0; x < shape[y].length; x++) {
                var stack = shape[y][x];
                if(stack != null) break;
                empties++;
            }
            if(empties != shape[y].length) {
                postRowCount = 0;
                preCheck = false;
            } else {
                if(preCheck) preRowCount++;
                postRowCount++;
            }
        }

        // grid empty
        if(preRowCount + postRowCount >= shape.length) return supplier.get(0, 0);
        preCheck = true;
        int preColCount = 0;
        int postColCount = 0;

        for (int x = 0; x < shape[0].length; x++) {
            var empties = 0;
            for (int y = 0; y < shape.length; y++) {
                var stack = shape[y][x];
                if(stack != null) break;
                empties++;
            }
            if(empties != shape.length) {
                postColCount = 0;
                preCheck = false;
            } else {
                if(preCheck) preColCount++;
                postColCount++;
            }
        }

        // Build new shape
        var outShape = supplier.get(shape.length-preRowCount-postRowCount, shape[0].length-preColCount-postColCount);

        for (int y = 0; y < outShape.length; y++) {
            System.arraycopy(shape[y + preRowCount], preColCount, outShape[y], 0, outShape[y].length);
        }

        return outShape;
    }

}
