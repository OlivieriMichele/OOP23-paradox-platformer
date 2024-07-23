package com.project.paradoxplatformer.view.game;


import com.project.paradoxplatformer.model.mappings.EntityDataMapper;
import com.project.paradoxplatformer.utils.InvalidResourceException;
import com.project.paradoxplatformer.view.graphics.GraphicAdapter;

public interface ViewMappingFactory {

    EntityDataMapper<GraphicAdapter> imageToView() throws InvalidResourceException;

    EntityDataMapper<GraphicAdapter> blockToView();
}
