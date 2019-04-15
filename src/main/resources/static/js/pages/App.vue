<template>
    <v-app>
        <v-navigation-drawer style="width: 100%" v-if="sideNav" v-model="sideNav">
            <v-list>
                <v-list-tile :to="{ name: 'Discussion' }">
                    <v-list-tile-content>{{$t('menu.discussion')}}</v-list-tile-content>
                </v-list-tile>
                <v-list-tile :to="{ name: 'ProjectsList' }">
                    <v-list-tile-content>{{$t('menu.projects')}}</v-list-tile-content>
                </v-list-tile>
                <v-list-tile :to="{ name: 'Profile', params: { userId: this.profile.id } }">
                    <v-list-tile-content>{{ profile.name }}</v-list-tile-content>
                </v-list-tile>
            </v-list>
        </v-navigation-drawer>
        <v-toolbar dark color="#455A64">
            <v-toolbar-side-icon
                    @click.stop="sideNav = !sideNav"
                    class="hidden-md-and-up">
            </v-toolbar-side-icon>
            <v-toolbar-title>
                <router-link :to="{ name: 'ProjectsList' }" tag="span" style="cursor: pointer">
                    Risk Management Tool
                </router-link>
            </v-toolbar-title>
            <v-spacer></v-spacer>
            <v-toolbar-items class="hidden-sm-and-down">
                <v-btn v-if="profile" flat
                       :disabled="$route.name === 'Discussion'"
                       @click="showDiscussion">
                    {{$t('menu.discussion')}}
                </v-btn>
                <v-btn v-if="profile" flat
                       :disabled="$route.name === 'ProjectsList'"
                       @click="showProjects">
                    {{$t('menu.projects')}}
                </v-btn>
                <v-btn v-if="profile" flat
                       :disabled="$route.name === 'Profile'"
                       @click="showProfile">
                    {{profile.name}}
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
        <main style="height: 100%">
            <router-view :key="$route.fullPath"></router-view>
        </main>
        <v-footer height="auto" style="width: 100%">
            <v-card class="text-xs-center" dark color="#455A64" width="100%">

                <v-card-text>
                    &copy;2019 — <strong>Nikitinho</strong>
                </v-card-text>
            </v-card>
        </v-footer>
    </v-app>
</template>

<script>
    import { mapState, mapMutations, mapActions } from 'vuex'
    import { addHandler } from 'util/ws'
    export default {
        data () {
            return {
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
            ...mapState(['profile'])
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
                console.log('Locale before: ' +  this.$i18n.locale)
                this.$i18n.locale = this.$i18n.locale === 'en' ? 'ru' : 'en'
                console.log('Locale after: ' +  this.$i18n.locale)
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
        }
    }
</script>

<style>
</style>