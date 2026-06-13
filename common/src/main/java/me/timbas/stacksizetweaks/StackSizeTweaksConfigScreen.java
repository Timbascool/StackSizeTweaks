package me.timbas.stacksizetweaks;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.IntegerFieldControllerBuilder;
import dev.isxander.yacl3.api.controller.StringControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.List;

import static me.timbas.stacksizetweaks.StackSizeTweaksConfig.HANDLER;

public class StackSizeTweaksConfigScreen {

    public static Screen createScreen(Screen parent) {

        var config = HANDLER.instance();

        return YetAnotherConfigLib.createBuilder()
                .title(Component.translatable("config.stacksizetweaks.title"))

                .category(ConfigCategory.createBuilder()
                        .name(Component.translatable("config.stacksizetweaks.category.general"))

                        .option(LabelOption.create(
                                Component.translatable("config.stacksizetweaks.label.info")
                        ))

                        .group(OptionGroup.createBuilder()
                                .name(Component.translatable("config.stacksizetweaks.group.item_groups"))
                                .description(OptionDescription.of(
                                        Component.translatable("config.stacksizetweaks.group.item_groups.description")
                                ))

                                .option(stackSizeOption(
                                        "config.stacksizetweaks.item_stack_limit",
                                        "config.stacksizetweaks.item_stack_limit.description",
                                        512,
                                        () -> config.itemStackLimit,
                                        value -> config.itemStackLimit = value
                                ))

                                .option(stackSizeOption(
                                        "config.stacksizetweaks.block_stack_limit",
                                        "config.stacksizetweaks.block_stack_limit.description",
                                        256,
                                        () -> config.blockStackLimit,
                                        value -> config.blockStackLimit = value
                                ))

                                .option(stackSizeOption(
                                        "config.stacksizetweaks.food_stack_limit",
                                        "config.stacksizetweaks.food_stack_limit.description",
                                        128,
                                        () -> config.foodStackLimit,
                                        value -> config.foodStackLimit = value
                                ))

                                .option(stackSizeOption(
                                        "config.stacksizetweaks.low_stack_limit",
                                        "config.stacksizetweaks.low_stack_limit.description",
                                        64,
                                        () -> config.lowStackLimit,
                                        value -> config.lowStackLimit = value
                                ))

                                .option(stackSizeOption(
                                        "config.stacksizetweaks.stew_stack_limit",
                                        "config.stacksizetweaks.stew_stack_limit.description",
                                        32,
                                        () -> config.stewStackLimit,
                                        value -> config.stewStackLimit = value
                                ))

                                .option(stackSizeOption(
                                        "config.stacksizetweaks.potion_stack_limit",
                                        "config.stacksizetweaks.potion_stack_limit.description",
                                        16,
                                        () -> config.potionStackLimit,
                                        value -> config.potionStackLimit = value
                                ))

                                .option(stackSizeOption(
                                        "config.stacksizetweaks.bucket_stack_limit",
                                        "config.stacksizetweaks.bucket_stack_limit.description",
                                        16,
                                        () -> config.bucketStackLimit,
                                        value -> config.bucketStackLimit = value
                                ))

                                .option(stackSizeOption(
                                        "config.stacksizetweaks.playable_stack_limit",
                                        "config.stacksizetweaks.playable_stack_limit.description",
                                        16,
                                        () -> config.playableStackLimit,
                                        value -> config.playableStackLimit = value
                                ))

                                .option(stackSizeOption(
                                        "config.stacksizetweaks.vehicle_stack_limit",
                                        "config.stacksizetweaks.vehicle_stack_limit.description",
                                        16,
                                        () -> config.playableStackLimit,
                                        value -> config.playableStackLimit = value
                                ))

                                .option(stackSizeOption(
                                        "config.stacksizetweaks.bed_stack_limit",
                                        "config.stacksizetweaks.bed_stack_limit.description",
                                        16,
                                        () -> config.bedStackLimit,
                                        value -> config.bedStackLimit = value
                                ))

                                .option(stackSizeOption(
                                        "config.stacksizetweaks.banner_pattern_stack_limit",
                                        "config.stacksizetweaks.banner_pattern_stack_limit.description",
                                        16,
                                        () -> config.bannerPatternStackLimit,
                                        value -> config.bannerPatternStackLimit = value
                                ))

                                .option(stackSizeOption(
                                        "config.stacksizetweaks.enchanted_book_stack_limit",
                                        "config.stacksizetweaks.enchanted_book_stack_limit.description",
                                        16,
                                        () -> config.enchantedBookStackLimit,
                                        value -> config.enchantedBookStackLimit = value
                                ))

                                .option(stackSizeOption(
                                        "config.stacksizetweaks.non_stackable_stack_limit",
                                        "config.stacksizetweaks.non_stackable_stack_limit.description",
                                        1,
                                        () -> config.nonStackableStackLimit,
                                        value -> config.nonStackableStackLimit = value
                                ))
                                .build())

                        .option(ListOption.<String>createBuilder()
                                .name(Component.translatable("config.stacksizetweaks.overrides"))

                                .description(OptionDescription.of(
                                        Component.translatable("config.stacksizetweaks.overrides.description")
                                ))

                                .binding(
                                        List.of("minecraft:example_item=64"),
                                        () -> HANDLER.instance().overrides,
                                        value -> HANDLER.instance().overrides = value
                                )
                                .controller(StringControllerBuilder::create)
                                .initial("")
                                .flag(OptionFlag.GAME_RESTART)
                                .build())

                        .group(OptionGroup.createBuilder()
                                .name(Component.translatable("config.stacksizetweaks.font_formatting"))

                                .option(boolOption(
                                        "config.stacksizetweaks.use_custom_font",
                                        "config.stacksizetweaks.use_custom_font.description",
                                        true,
                                        () -> config.useCustomFont,
                                        value -> config.useCustomFont = value
                                ))

                                .option(boolOption(
                                        "config.stacksizetweaks.shorten_item_amounts",
                                        "config.stacksizetweaks.shorten_item_amounts.description",
                                        true,
                                        () -> config.shortenItemAmounts,
                                        value -> config.shortenItemAmounts = value
                                ))
                                .build())
                        .build())

                .save(HANDLER::save)
                .build()
                .generateScreen(parent);
    }


    private static Option<Integer> stackSizeOption(
            String textKey,
            String descriptionKey,
            int defaultValue,
            java.util.function.Supplier<Integer> getter,
            java.util.function.Consumer<Integer> setter
    ) {
        return Option.<Integer>createBuilder()
                .name(Component.translatable(textKey))
                .description(OptionDescription.of(Component.translatable(descriptionKey)))
                .binding(defaultValue, getter, setter)
                .controller(opt -> IntegerFieldControllerBuilder.create(opt)
                        .range(0, StackSizeTweaks.ABSOLUTE_MAX_STACK_SIZE))
                .flag(OptionFlag.GAME_RESTART)
                .build();
    }

    private static Option<Boolean> boolOption(
            String textKey,
            String descriptionKey,
            boolean defaultValue,
            java.util.function.Supplier<Boolean> getter,
            java.util.function.Consumer<Boolean> setter
    ) {
        return Option.<Boolean>createBuilder()
                .name(Component.translatable(textKey))
                .description(OptionDescription.of(Component.translatable(descriptionKey)))
                .binding(defaultValue, getter, setter)
                .controller(TickBoxControllerBuilder::create)
                .build();
    }
}