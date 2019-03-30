<template>
    <v-card class="my-2">
        <v-card-title>
            <v-icon large left>
                star_border
            </v-icon>
            <span class="headline font-weight-bold">{{ risk.text }}</span>
        </v-card-title>
        <v-card-text class="title font-weight-light">
            {{ risk.description }}
        </v-card-text>
        <v-card-actions>
            <v-list-tile class="grow">
                <span v-for="user in risk.responsible">
                <v-list-tile-avatar color="grey darken-3">
                    <v-img class="elevation-6"
                           :src="user.userpic">
                    </v-img>
                </v-list-tile-avatar>
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
            ...mapActions(['removeRiskAction', 'updateRiskAction']),
            edit () {
                this.updateRiskAction(this.risk)
            },
            del () {
                this.removeRiskAction(this.risk)
            }
        }
    }
</script>

<style>

</style>