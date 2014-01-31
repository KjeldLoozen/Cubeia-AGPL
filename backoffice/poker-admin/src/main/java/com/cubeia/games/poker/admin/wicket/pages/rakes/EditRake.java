/**
 * Copyright (C) 2012 BetConstruct
 */

package com.cubeia.games.poker.admin.wicket.pages.rakes;

import java.math.BigDecimal;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.cubeia.games.poker.admin.db.AdminDAO;
import com.cubeia.games.poker.admin.wicket.BasePage;
import com.cubeia.poker.settings.RakeSettings;

@AuthorizeInstantiation({"ROLE_ADMIN"})
public class EditRake extends BasePage {

	private static final long serialVersionUID = 1L;

	@SpringBean(name="adminDAO")
    private AdminDAO adminDAO;

    private RakeSettings rake;

    public EditRake(final PageParameters parameters) {
        super(parameters);
        final Integer templateId = parameters.get("templateId").toInt();
        loadFormData(templateId);
        Form<RakeSettings> rakeForm = new Form<RakeSettings>("rakeForm", new CompoundPropertyModel<RakeSettings>(rake)) {

            private static final long serialVersionUID = 1L;

            @Override
            protected void onSubmit() {
                RakeSettings object = getModel().getObject();
                adminDAO.merge(object);
                // info("Rake configuration updated, id = " + templateId);
                setResponsePage(ListRakes.class);
            }
        };

        rakeForm.add(new RequiredTextField<String>("name", new PropertyModel<String>(this, "rake.name")));
        rakeForm.add(new RequiredTextField<BigDecimal>("rakeFraction2Plus", new PropertyModel<BigDecimal>(this, "rake.rakeFraction2Plus")));
        rakeForm.add(new RequiredTextField<Long>("rakeLimit2Plus", new PropertyModel<Long>(this, "rake.rakeLimit2Plus")));
        rakeForm.add(new RequiredTextField<BigDecimal>("rakeFraction3Plus", new PropertyModel<BigDecimal>(this, "rake.rakeFraction3Plus")));
        rakeForm.add(new RequiredTextField<Long>("rakeLimit3Plus", new PropertyModel<Long>(this, "rake.rakeLimit3Plus")));
        rakeForm.add(new RequiredTextField<BigDecimal>("rakeFraction5Plus", new PropertyModel<BigDecimal>(this, "rake.rakeFraction5Plus")));
        rakeForm.add(new RequiredTextField<Long>("rakeLimit5Plus", new PropertyModel<Long>(this, "rake.rakeLimit5Plus")));

        add(rakeForm);

        add(new FeedbackPanel("feedback"));
    }

    private void loadFormData(final Integer timingId) {
        rake = adminDAO.getItem(RakeSettings.class, timingId);
    }

    @Override
    public String getPageTitle() {
        return "Edit Rake Configuration";
    }
}