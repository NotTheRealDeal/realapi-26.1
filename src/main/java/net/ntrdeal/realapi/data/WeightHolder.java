package net.ntrdeal.realapi.data;

import com.mojang.serialization.DataResult;
import org.apache.commons.lang3.math.Fraction;

public interface WeightHolder {
    DataResult<Fraction> weight();
}
