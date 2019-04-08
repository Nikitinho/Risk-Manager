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
        <v-toolbar dark>
            <v-toolbar-side-icon
                    @click.stop="sideNav = !sideNav"
                    class="hidden-md-and-up ">
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
        <main>
            <router-view :key="$route.fullPath"></router-view>
        </main>
    </v-app>
</template>

<script>
    import { mapState, mapMutations } from 'vuex'
    import { addHandler } from 'util/ws'
    export default {
        data () {
            return {
                showListOptions: true,
                sideNav: false,
                menuItems: []
            }
        },
        computed: {
            ...mapState(['profile'])
        },
        methods: {
            ...mapMutations(['addRiskMutation', 'updateRiskMutation', 'removeRiskMutation']),
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
                            this.addRiskMutation(data.body)
                            break
                        case 'UPDATE':
                            this.updateRiskMutation(data.body)
                            break
                        case 'REMOVE':
                            this.removeRiskMutation(data.body)
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