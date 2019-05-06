<template>
    <!-- components best usage https://demos.creative-tim.com/vuetify-material-dashboard/ -->
    <!-- https://demos.creative-tim.com/vuetify-material-dashboard/documentation/#getting-started -->
    <v-app class="app">
        <!-- v-navigation-drawer example https://codepen.io/kematzy/pen/NzQqOR -->
        <v-navigation-drawer permanent
                             app>
            <v-layout justify-start column fill-height
                      class="drawer_title">
                <v-list>
                    <v-list-tile avatar>
                        <!--<v-list-tile-avatar>-->
                        <!--<v-img-->
                        <!--src="https://cdn1.imggmi.com/uploads/2019/5/4/58f4211e92e5b66c5d9cb6529e646b38-full.png"-->
                        <!--aspect-ratio="1"-->
                        <!--&gt;-->
                        <!--</v-img>-->
                        <!--</v-list-tile-avatar>-->
                        <v-list-tile-title class="white--text title">
                            Risk Management Tool
                        </v-list-tile-title>
                    </v-list-tile>
                    <v-text-field
                            :placeholder="$t('constants.search')"
                            class="elevation-0 mx-2 py-1"
                            hide-details
                            v-model="searchField"
                            single-line dark box>
                    </v-text-field>
                    <v-list v-if="areProjectTypesShown" class="drawer" dense>
                        <v-list-tile v-for="(type, index) in projectTypes"
                                     :dark="true"
                                     hide-details
                                     class="pt mx-3"
                        >
                            <v-list-tile-action>
                                <v-checkbox :ripple="false"
                                            @change="() => chooseProjectType(index)"
                                            color="red"
                                            :input-value="type.isShown" value
                                            :label="$t(`project.types.${type.value.toLowerCase()}`)">
                                </v-checkbox>
                            </v-list-tile-action>
                        </v-list-tile>
                    </v-list>
                    <v-list>
                    <v-list-tile
                            @click="showProjectTypes"
                            class="white--text tile mx-3"
                    >
                        <v-list-tile-action>
                            <v-icon color="white">aspect_ratio</v-icon>
                        </v-list-tile-action>
                        <v-list-tile-title
                                v-text="$t('menu.projectsTypes')"
                        />
                    </v-list-tile>
                    <v-list-tile
                            @click="showDiscussion"
                            class="white--text tile mx-3"
                    >
                        <v-list-tile-action>
                            <v-icon color="white">message</v-icon>
                        </v-list-tile-action>
                        <v-list-tile-title
                                v-text="$t('menu.discussion')"
                        />
                    </v-list-tile>
                    <v-list-tile
                            @click="showProjects"
                            class="white--text tile mx-3"
                    >
                        <v-list-tile-action>
                            <v-icon color="white">assignment</v-icon>
                        </v-list-tile-action>
                        <v-list-tile-title
                                v-text="$t('menu.projects')"
                        />
                    </v-list-tile>
                    </v-list>
                </v-list>
            </v-layout>
        </v-navigation-drawer>
        <v-toolbar absolute app flat class="toolbar">
            <v-toolbar-side-icon
                    @click.stop="isSlideOpen = !isSlideOpen"
                    class="hidden-md-and-up">
            </v-toolbar-side-icon>
            <v-toolbar-title>
                ProjectsList
            </v-toolbar-title>
            <v-btn v-if="profile" icon
                   @click="createProject"
                   class="hidden-sm-and-down">
                <v-icon>add</v-icon>
            </v-btn>
            <v-spacer></v-spacer>
            <v-toolbar-items>
                <v-btn icon @click="() => this.$router.back()">
                    <v-icon dark>chevron_left</v-icon>
                </v-btn>
                <v-btn v-if="profile" icon
                       :disabled="$route.name === 'Profile'"
                       @click="showProfile"
                       class="hidden-sm-and-down">
                    <v-icon>person</v-icon>
                </v-btn>
                <v-btn v-if="profile" icon
                       @click="changeLocale">
                    <v-icon>public</v-icon>
                </v-btn>
                <v-btn v-if="profile" icon href="/logout">
                    <v-icon>exit_to_app</v-icon>
                </v-btn>
            </v-toolbar-items>
        </v-toolbar>
        <v-content>
            <main>
                <router-view :key="$route.fullPath"
                             :projectTypes="projectTypes">
                </router-view>
            </main>
        </v-content>
    </v-app>
</template>

<script>
    import { mapState, mapMutations, mapActions, mapGetters } from 'vuex'
    import { addHandler } from 'util/ws'
    import { Slide } from 'vue-burger-menu'
    import CookieLaw from 'vue-cookie-law'
    export default {
        data () {
            return {
                searchField: '',
                projectTypes: [],
                areProjectTypesShown: false,
                isSlideOpen: false,
                showListOptions: true,
                sideNav: false,
                icons: {
                    'github': {img: 'https://github.githubassets.com/images/modules/logos_page/GitHub-Mark.png',
                        link: 'https://github.com/Nikitinho' },
                    'tg': {img: 'https://upload.wikimedia.org/wikipedia/commons/thumb/8/82/Telegram_logo.svg/1024px-Telegram_logo.svg.png',
                        link: 'http://t.me/nikitinho_jr'}
                }
            }
        },
        computed: {
            ...mapState(['profile']),
            ...mapGetters(['getProjectTypes'])
        },
        methods: {
            ...mapMutations(['addProjectMutation', 'updateProjectMutation', 'removeProjectMutation']),
            ...mapActions(['addRiskRefresh', 'updateRiskRefresh', 'removeRiskRefresh',
                'addBoardRefresh', 'updateBoardRefresh', 'removeBoardRefresh',
                'addBoardItemRefresh', 'updateBoardItemRefresh', 'removeBoardItemRefresh']),
            showDiscussion() {
                this.$router.push({ name: 'Discussion' })
            },
            showProfile() {
                this.$router.push({ name: 'Profile', params: { userId: this.profile.id } })
            },
            showProjects() {
                this.$router.push({ name: 'ProjectsList' })
            },
            createProject() {
                this.$router.push({ name: 'ProjectCreation' })
            },
            changeLocale() {
                this.$i18n.locale = this.$i18n.locale === 'en' ? 'ru' : 'en'
                if (localStorage) {
                    localStorage.locale = this.$i18n.locale
                }
            },
            showProjectTypes() {
                this.areProjectTypesShown = !this.areProjectTypesShown
            },
            createProject() {
                this.$router.push({ name: 'ProjectCreation' })
            },
            chooseProjectType(index) {
                let type = this.projectTypes[index]
                type.isShown = !type.isShown
                this.$set(this.projectTypes, index, type)
            }
        },
        created() {
            addHandler(data => {
                if (data.objectType === 'RISK') {
                    switch (data.eventType) {
                        case 'CREATE':
                            this.addRiskRefresh(data.body)
                            break
                        case 'UPDATE':
                            this.updateRiskRefresh(data.body)
                            break
                        case 'REMOVE':
                            this.removeRiskRefresh(data.body)
                            break
                        default:
                            console.error(`Event type ${data.eventType} is unknown`)
                    }
                } else if (data.objectType === 'PROJECT') {
                    switch (data.eventType) {
                        case 'CREATE':
                            this.addProjectMutation(data.body)
                            break
                        case 'UPDATE':
                            this.updateProjectMutation(data.body)
                            break
                        case 'REMOVE':
                            this.removeProjectMutation(data.body)
                            break
                        default:
                            console.error(`Event type ${data.eventType} is unknown`)
                    }
                } else if (data.objectType === 'BOARD') {
                    switch (data.eventType) {
                        case 'CREATE':
                            this.addBoardRefresh(data.body)
                            break
                        case 'UPDATE':
                            this.updateBoardRefresh(data.body)
                            break
                        case 'REMOVE':
                            this.removeBoardRefresh(data.body)
                            break
                        default:
                            console.error(`Event type ${data.eventType} is unknown`)
                    }
                } else if (data.objectType === 'BOARD_ITEM') {
                    switch (data.eventType) {
                        case 'CREATE':
                            this.addBoardItemRefresh(data.body)
                            break
                        case 'UPDATE':
                            this.updateBoardItemRefresh(data.body)
                            break
                        case 'REMOVE':
                            this.removeBoardItemRefresh(data.body)
                            break
                        default:
                            console.error(`Event type ${data.eventType} is unknown`)
                    }
                }
                else {
                    console.error(`Object type ${data.objectType} is unknown`)
                }
            })
            // ProjectTypes
            this.getProjectTypes.forEach(type => this.projectTypes.push({value: type, isShown: false}))
        },
        mounted() {
            if (localStorage && localStorage.locale) {
                this.$i18n.locale = localStorage.locale
            }
        },
        beforeMount() {
            if (!this.profile) {
                this.$router.replace({ name: 'GoogleAuth' })
            }
        },
        addMenuItems() {
            this.menuItems.push({ icon: 'supervisor_account', title: 'ProjectsList', link: '/' })
            this.menuItems.push({ icon: 'lock_open',
                title: 'Profile',
                link: this.$router.options.routes.find(x=>x.name === 'Profile').path + '/' + this.profile.id
            })
        },
        components: {
            Slide,
            CookieLaw
        }
    }
</script>

<style scoped>
    .pt {
        margin: 5px;
        border-radius: 10px;
    }
    .pt:hover {
        background: #1E2A42;
    }
    .pt:active {
        background: #1E2A42;
    }
    .tile {
        margin: 2px;
        border-radius: 10px;
    }
    .tile:hover {
        background: #5878FF;
    }
    .tile:active {
        background: #5878FF;
    }
</style>