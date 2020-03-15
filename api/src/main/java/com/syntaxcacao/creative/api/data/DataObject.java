package com.syntaxcacao.creative.api.data;

import org.bukkit.World;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Charles Müller
 */
@AllArgsConstructor
@Getter
public abstract class DataObject
{
    private String name;
    private transient @Setter World context;
}
