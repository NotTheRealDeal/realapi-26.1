package net.ntrdeal.realapi.client.render;

import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import org.jetbrains.annotations.ApiStatus;

import java.util.Arrays;
import java.util.List;

@ApiStatus.NonExtendable
public interface RealModelUtils {
    static FirstPerson firstPerson() {return new FirstPerson();}
    static LeftHand leftHand() {return new LeftHand();}

    static MultiProperty multi(ConditionalItemModelProperty... properties) {
        return multi(Arrays.asList(properties));
    }

    static MultiProperty multi(List<ConditionalItemModelProperty> properties) {
        return new MultiProperty(properties);
    }
}