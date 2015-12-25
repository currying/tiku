package com.toparchy.molecule.tiku.controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.inject.Model;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.tagcloud.TagCloudItem;

import com.toparchy.molecule.tiku.data.TagRepository;
import com.toparchy.molecule.tiku.data.TopicRepository;
import com.toparchy.molecule.tiku.model.Tag;
import com.toparchy.molecule.tiku.model.Topic;

@Model
@ViewScoped
public class TagView implements Serializable {

}
