<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-card class="my-2">
        <v-card-title>
            <span v-if="risk.status === 'CLOSED'">
            <!--<v-icon large color="red" left>-->
                <v-icon left>
                    check
                </v-icon>
            </span>
            <span v-if="risk.status === 'IN_THE_WORK'">
                <v-icon left>
                    error
                </v-icon>
            </span>
            <span v-if="risk.status === 'CREATED'">
                <v-icon left>
                    access_time
                </v-icon>
            </span>
            <span class="headline font-weight-bold">
                <router-link :to="{ name: 'RiskCreation', params: { riskId: this.risk.id, readonly: true } }">
                    {{ risk.text }}
                </router-link>
            </span>
        </v-card-title>
        <v-card-text class="title font-weight-light">
            {{ risk.description }}
        </v-card-text>
        <v-divider></v-divider>
        <v-card-actions>
            <v-list-tile class="grow">
                <span v-for="user in risk.responsible">
                    <v-tooltip bottom>
                        <template v-slot:activator="{ on }">
                            <v-list-tile-avatar color="grey darken-3">
                            <v-img class="elevation-6"
                            :src="user.userpic"
                                   v-on="on">
                            </v-img>
                            </v-list-tile-avatar>
                        </template>
                        <span>{{ user.name }}</span>
                    </v-tooltip>
                </span>
                <v-layout align-center justify-end>
                    <v-btn fab flat @click="edit">
                        <v-icon dark>edit</v-icon>
                    </v-btn>
                    <v-btn fab flat @click="del">
                        <v-icon dark>delete</v-icon>
                    </v-btn>
                </v-layout>
            </v-list-tile>
        </v-card-actions>
    </v-card>
</template>

<script>
    import {mapActions} from 'vuex'
    export default {
        props: ['risk'],
        methods: {
            ...mapActions(['removeRiskAction']),
            edit () {
                this.$router.push({ name: 'RiskCreation', params: { riskId: this.risk.id, readonly: false } })
            },
            del () {
                this.removeRiskAction(this.risk)
            }
        }
    }
</script>

<style>

</style>