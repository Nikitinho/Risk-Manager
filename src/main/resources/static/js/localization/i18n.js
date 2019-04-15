import Vue from 'vue'
import VueI18n from 'vue-i18n'
// import en modules
import en_menu from './en_modules/menu.json'
import en_projectsList from './en_modules/projectsList.json'
import en_project from './en_modules/project.json'
import en_profile from './en_modules/profile.json'
import en_discussion from './en_modules/discussion.json'
import en_risk from './en_modules/risk.json'
import en_constants from './en_modules/constants.json'
// import ru modules
import ru_menu from './ru_modules/menu.json'
import ru_projectsList from './ru_modules/projectsList.json'
import ru_project from './ru_modules/project.json'
import ru_profile from './ru_modules/profile.json'
import ru_discussion from './ru_modules/discussion.json'
import ru_risk from './ru_modules/risk.json'
import ru_constants from './ru_modules/constants.json'

Vue.use(VueI18n)

// Guide: https://stackoverflow.com/questions/736590/add-new-attribute-element-to-json-object-using-javascript
export const i18n = new VueI18n({
    locale: 'en',
    fallbackLocale: 'ru',
    messages: {
        en: {
            constants: en_constants,
            menu: en_menu,
            projectsList: en_projectsList,
            project: en_project,
            profile: en_profile,
            discussion: en_discussion,
            risk: en_risk
        },
        ru: {
            constants: ru_constants,
            menu: ru_menu,
            projectsList: ru_projectsList,
            project: ru_project,
            profile: ru_profile,
            discussion: ru_discussion,
            risk: ru_risk
        }
    }
})