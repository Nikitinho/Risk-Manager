<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-container fluid>
        <v-layout align-space-around justify-start wrap>
            <v-flex xs8 class="px-1 py-1">
            <v-card class="mb-1">
                <v-card-title>
                    <span class="headline font-weight-light">{{ project.name }}</span>
                </v-card-title>
                <v-divider></v-divider>
                <v-card-text>
                    {{ project.description }}
                </v-card-text>
            </v-card>
                <v-card>
                    <v-card-title>
                        <span class="headline font-weight-light">Risks List</span>
                        <v-spacer></v-spacer>
                        <v-btn color="success" @click="createRisk">
                            <v-icon dark>add</v-icon>
                        </v-btn>
                    </v-card-title>
                    <v-list>
                        <template v-for="risk in project.risks" :v-key="risk.id">

                            <v-divider></v-divider>

                            <v-list-tile avatar
                                         @click="() => { showRisk(risk) }">
                                <!--<v-list-tile-avatar v-if="user.userpic">-->
                                    <!--<v-img class="elevation-6"-->
                                           <!--:src="user.userpic">-->
                                    <!--</v-img>-->
                                <!--</v-list-tile-avatar>-->

                                <v-list-tile-content>
                                    <v-list-tile-title v-html="risk.text"></v-list-tile-title>
                                </v-list-tile-content>
                            </v-list-tile>
                        </template>
                    </v-list>
                </v-card>
            </v-flex>
            <v-flex xs4 class="px-1 py-1">
                <v-card>
                    <v-card-title>
                        <span class="headline font-weight-light">Project team</span>
                        <v-spacer></v-spacer>
                        <v-btn color="success"
                               @click="() => { this.isTeamShown = !this.isTeamShown }">
                            {{ isTeamShown ? 'Hide team' : 'Show team' }}
                        </v-btn>
                    </v-card-title>
                    <v-list v-if="isTeamShown">
                        <template v-for="user in project.responsible" :v-key="user.id">

                            <v-divider></v-divider>

                            <v-list-tile avatar
                                         @click="">
                                <v-list-tile-avatar v-if="user.userpic">
                                    <v-img class="elevation-6"
                                           :src="user.userpic">
                                    </v-img>
                                </v-list-tile-avatar>

                                <v-list-tile-content>
                                    <v-list-tile-title v-html="user.name"></v-list-tile-title>
                                </v-list-tile-content>
                            </v-list-tile>
                        </template>
                    </v-list>
                </v-card>
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
    import { mapGetters } from 'vuex'

    export default {
        name: 'ProjectView',
        props: ['projectId'],
        data() {
            return {
                project: null,
                isTeamShown: true
            }
        },
        computed: mapGetters(['getProjectById']),
        created () {
//            console.log(this.projectId)
            this.project = this.getProjectById((Number)(this.projectId))
            console.log(this.project)
//            console.log(this.project)
        },
        methods: {
            createRisk() {
                this.$router.push({ name: 'RiskCreation', params: { projectId: this.project.id } })
            },
            showRisk(risk) {
                this.$router.push({ name: 'RiskView', params: { riskId: risk.id } })
            }
        }
    }
</script>

<style>
    .rounded-v-select {
        border-radius:50px;
    }
    .this-p
    {
        font-size:0.9em !important;
        font-weight: bold;
    }
