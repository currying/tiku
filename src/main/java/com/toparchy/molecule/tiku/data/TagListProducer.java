package com.toparchy.molecule.tiku.data;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.tagcloud.DefaultTagCloudItem;
import org.primefaces.model.tagcloud.DefaultTagCloudModel;
import org.primefaces.model.tagcloud.TagCloudModel;

import com.toparchy.molecule.tiku.model.Tag;

@Model
@RequestScoped
public class TagListProducer {
	@Inject
	private TagRepository tagRepository;

	private List<Tag> tags;
	private TagCloudModel model;

	@Produces
	@Named
	public List<Tag> getTags() {
		return tags;
	}

	public void onTagListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Tag tag) {
		retrieveAllTagsOrderedByName();
	}

	@PostConstruct
	public void retrieveAllTagsOrderedByName() {
		tags = tagRepository.findFromTo(0, 100);
		model = new DefaultTagCloudModel();
		for (Tag tag : tags) {
			model.addTag(new DefaultTagCloudItem(tag.getTagName(), (int) ((Math.random()) * 5 + 1)));
		}
	}

	public TagCloudModel getModel() {
		return model;
	}
}
