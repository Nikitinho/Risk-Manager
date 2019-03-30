<template>
    <v-app>
        <v-toolbar app>
            <v-toolbar-title>Risk management tool</v-toolbar-title>
            <v-btn v-if="profile" flat round
                   :disabled="$route.path === '/'"
                   @click="showRisks">
                Home
            </v-btn>
            <v-btn v-if="profile" flat round
                   :disabled="$route.path === '/newRisk'"
                   @click="createRisk">
                New
            </v-btn>
            <v-spacer></v-spacer>
            <v-btn v-if="profile" round
                   :disabled="$route.path === '/profile'"
                   @click="showProfile">
                {{profile.name}}
            </v-btn>
            <v-btn v-if="profile" icon href="/logout">
                <v-icon>exit_to_app</v-icon>
            </v-btn>
        </v-toolbar>
        <v-content>
            <router-view></router-view>
        </v-content>
    </v-app>
</template>

<script>
    import { mapState, mapMutations } from 'vuex'
    import {addHandler} from 'util/ws';
    export default {
        computed: mapState(['profile']),
        methods: {
            ...mapMutations(['addRiskMutation', 'updateRiskMutation', 'removeRiskMutation']),
            showRisks() {
                this.$router.push('/')
            },
            createRisk() {
                this.$router.push('/newRisk')
            },
            showProfile() {
                this.$router.push('/profile')
            }
        }
        ,
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
                this.$router.replace('/auth')
            }
        }
    }
</script>

<style>
</style>