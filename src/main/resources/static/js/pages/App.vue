<template>
    <v-app>
        <v-toolbar app>
            <v-toolbar-title>Risk management tool</v-toolbar-title>
            <v-spacer></v-spacer>
            <span v-if="profile">{{profile.name}}</span>
            <v-btn v-if="profile" icon href="/logout">
                <v-icon>exit_to_app</v-icon>
            </v-btn>
        </v-toolbar>
        <v-content>
            <v-container v-if="!profile">Необходимо авторизоваться через
                <a href="/login">Google</a>
            </v-container>
            <v-container v-if="profile">
                <risks-list :risks="risks" />
            </v-container>
        </v-content>
    </v-app>
</template>

<script>
    import RisksList from 'components/risks/RisksList.vue'
    import {addHandler} from 'util/ws';
    import {getIndex} from 'util/collections'
    export default {
        components: {
            RisksList
        },
        data() {
            return {
                risks: frontendData.risks,
                profile: frontendData.profile
            }
        },
        created() {
            addHandler(data => {
                let index = getIndex(this.risks, data.id)
                if (index > -1) {
                    this.risks.splice(index, 1, data)
                } else {
                    this.risks.push(data)
                }
            })
        }
    }
</script>

<style>
</style>