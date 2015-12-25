package com.toparchy.molecule.tiku.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import com.toparchy.molecule.tiku.data.ChapterRepository;
import com.toparchy.molecule.tiku.model.Chapter;
import com.toparchy.molecule.tiku.model.KnowledgePoint;
import com.toparchy.molecule.tiku.service.KnowledgePointRegistration;

@Model
@ViewScoped
public class KnowledgePointController implements Serializable {

}
