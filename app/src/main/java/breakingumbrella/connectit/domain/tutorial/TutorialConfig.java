package breakingumbrella.connectit.domain.tutorial;

import javax.inject.Inject;

import breakingumbrella.connectit.configuration.ConfigKeys;
import breakingumbrella.connectit.configuration.GlobalConfig;
import breakingumbrella.connectit.domain.tutorial.steps.InitialStep;
import breakingumbrella.connectit.domain.tutorial.steps.PutFigureDiagonal;
import breakingumbrella.connectit.domain.tutorial.steps.PutFigureLongLine;
import breakingumbrella.connectit.domain.tutorial.steps.PutFigureStep;
import breakingumbrella.connectit.domain.tutorial.steps.PutFigureWithEnemyStep;
import breakingumbrella.connectit.domain.tutorial.steps.UseAbilityStep;

public class TutorialConfig {

    private GlobalConfig globalConfig;

    @Inject
    public TutorialConfig(GlobalConfig globalConfig) {
        this.globalConfig = globalConfig;
    }

    public String getTutorialText(Class tutorialStep) {
        return getTutorialText(tutorialStep, 0);
    }

    public String getTutorialText(Class tutorialStep, int txtNumber) {
        String localizedText;
        if (tutorialStep == InitialStep.class) {
            localizedText = globalConfig.getLocalizedText(ConfigKeys.init_step);
        } else if (tutorialStep == PutFigureStep.class) {
            localizedText = globalConfig.getLocalizedText(ConfigKeys.put_figure_step);
        } else if (tutorialStep == PutFigureWithEnemyStep.class) {
            localizedText = globalConfig.getLocalizedText(ConfigKeys.put_figure_with_enemy);
        } else if (tutorialStep == PutFigureLongLine.class) {
            if (txtNumber == 0) {
                localizedText = globalConfig.getLocalizedText(ConfigKeys.put_figure_long_line);
            } else {
                localizedText = globalConfig.getLocalizedText(ConfigKeys.look_at_the_score);
            }
        } else if (tutorialStep == PutFigureDiagonal.class) {
            localizedText = globalConfig.getLocalizedText(ConfigKeys.put_figure_diagonal);
        } else if (tutorialStep == UseAbilityStep.class) {
            if(txtNumber == 0) {
                localizedText = globalConfig.getLocalizedText(ConfigKeys.use_ability);
            }
            else {
                localizedText = globalConfig.getLocalizedText(ConfigKeys.try_To_Block);
            }
        } else {
            localizedText = "Missing tutorial step";
        }
        return localizedText;
    }

    public String getTutorialName(Class tutorialStep) {
        String localizedText;
        if (tutorialStep == InitialStep.class) {
            localizedText = globalConfig.getLocalizedText(ConfigKeys.init_step_name);
        } else if (tutorialStep == PutFigureStep.class) {
            localizedText = globalConfig.getLocalizedText(ConfigKeys.put_figure_step_name);
        } else if (tutorialStep == PutFigureWithEnemyStep.class) {
            localizedText = globalConfig.getLocalizedText(ConfigKeys.put_figure_with_enemy_name);
        } else if (tutorialStep == PutFigureLongLine.class) {
            localizedText = globalConfig.getLocalizedText(ConfigKeys.put_figure_long_line_name);
        } else if (tutorialStep == PutFigureDiagonal.class) {
            localizedText = globalConfig.getLocalizedText(ConfigKeys.put_figure_diagonal_name);
        } else if (tutorialStep == UseAbilityStep.class) {
            localizedText = globalConfig.getLocalizedText(ConfigKeys.use_ability_name);
        } else {
            localizedText = "Missing tutorial step";
        }
        return localizedText;
    }

}
