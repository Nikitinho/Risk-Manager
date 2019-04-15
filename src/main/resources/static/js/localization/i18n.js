import Vue from 'vue'
import VueI18n from 'vue-i18n'
// import en modules
import en_menu from './en_modules/menu.json'
import en_projectsList from './en_modules/projecsList.json'
import en_project from './en_modules/project.json'
// import ru modules
import ru_menu from './ru_modules/menu.json'
import ru_projectsList from './ru_modules/projectsList.json'
import ru_project from './ru_modules/project.json'

Vue.use(VueI18n)

// Guide: https://stackoverflow.com/questions/736590/add-new-attribute-element-to-json-object-using-javascript
export const i18n = new VueI18n({
    locale: 'en',
    fallbackLocale: 'ru',
    messages: {
        en: {
            menu: en_menu,
            projectsList: en_projectsList,
            project: en_project
        },
        ru: {
            menu: ru_menu,
            projectsList: ru_projectsList,
            project: ru_project
        }
    }
})