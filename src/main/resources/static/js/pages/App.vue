<template>
    <v-app>
        <!--<v-navigation-drawer v-if="sideNav" v-model="sideNav">-->
            <!--<v-list>-->
                <!--<v-list-tile-->
                        <!--v-for="item in menuItems"-->
                        <!--:key="item.title"-->
                        <!--:to="item.link">-->
                    <!--<v-list-tile-action>-->
                        <!--<v-icon>{{ item.icon }}</v-icon>-->
                    <!--</v-list-tile-action>-->
                    <!--<v-list-tile-content>{{ item.title }}</v-list-tile-content>-->
                <!--</v-list-tile>-->
            <!--</v-list>-->
        <!--</v-navigation-drawer>-->
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
                       :disabled="$route.name === 'ProjectsList'"
                       @click="showProjects">
                    Projects
                </v-btn>
                <v-btn v-if="profile" flat
                       :disabled="$route.name === 'Profile'"
                       @click="showProfile">
                    {{profile.name}}
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
                    <v-btn class="mx-3"
                           large icon
                           :href="icons.github.link">
                        <v-avatar>
                            <v-img style="height: 100%;" :src="icons.github.img" />
                        </v-avatar>
                    </v-btn>
                    <v-btn class="mx-3"
                           large icon
                           :href="icons.tg.link">
                        <v-avatar>
                            <v-img style="height: 100%;" :src="icons.tg.img" />
                        </v-avatar>
                    </v-btn>
                </v-card-text>

                <v-divider></v-divider>

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
                menuItems: [],
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
            ...mapActions(['addRiskRefresh', 'updateRiskRefresh', 'removeRiskRefresh']),
            showProfile() {
                this.$router.push({ name: 'Profile', params: { userId: this.profile.id } })
            },
            showProjects() {
                this.$router.push({ name: 'ProjectsList' })
            },
            createProject() {
                this.$router.push({ name: 'ProjectCreation' })
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
                } else {
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