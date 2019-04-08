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
                                <v-list-tile-avatar>
                                    <v-icon :color="getColor(risk.riskRate)">report</v-icon>
                                </v-list-tile-avatar>

                                <v-list-tile-content>
                                    <v-list-tile-title v-html="risk.text"></v-list-tile-title>
                                </v-list-tile-content>

                                <v-spacer></v-spacer>

                                <div class="text-xs-center">
                                    <v-chip>{{getRiskStatus(risk)}}</v-chip>
                                </div>

                                <v-divider vertical></v-divider>

                                <v-list-tile-avatar v-for="user in getResponsible(risk)">
                                    <v-img class="elevation-6"
                                           :src="user.userpic">
                                    </v-img>
                                </v-list-tile-avatar>

                                <v-list-tile-avatar v-if="getExtraRiskUsersAmount(risk) > 0">
                                    <span class="cetered-text-span">+{{getExtraRiskUsersAmount(risk)}}</span>
                                </v-list-tile-avatar>

                            </v-list-tile>
                        </template>
                    </v-list>
                </v-card>
            </v-flex>
            <v-flex xs4 class="px-1 py-1">
                <v-card class="mb-1">
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
                                         @click="() => showProfile(user.id)">
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
                <v-card>
                    <v-card-title>
                        <span class="headline font-weight-light">Graph</span>
                        <v-spacer></v-spacer>
                        <v-btn icon @click="() => this.isBubbleChartShown = !this.isBubbleChartShown">
                            <v-icon dark>loop</v-icon>
                        </v-btn>
                        <v-btn color="success"
                               @click="() => { this.isGraphShown = !this.isGraphShown }">
                            {{ isGraphShown ? 'Hide' : 'Show' }}
                        </v-btn>
                    </v-card-title>
                    <v-divider></v-divider>
                    <slot v-if="isGraphShown">
                        <slot v-if="project.risks && project.risks.length > 0">
                            <bubble-chart v-if="isBubbleChartShown" :data="project.risks"></bubble-chart>
                            <doughnut-chart v-else :data="project.risks"></doughnut-chart>
                        </slot>
                        <slot v-else>
                            <v-card-text>
                                Nothing to show here
                            </v-card-text>
                        </slot>
                    </slot>
                </v-card>
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
    import { mapGetters } from 'vuex'
    import BubbleChart from 'components/risks/BubbleChart.vue'
    import DoughnutChart from 'components/risks/DoughnutChart.vue'
    import Risk from 'domain/Risk'

    export default {
        name: 'ProjectView',
        props: ['projectId'],
        data() {
            return {
                project: null,
                isTeamShown: true,
                isGraphShown: true,
                isBubbleChartShown: true,
                maxRiskUsersAmount: 3
            }
        },
        computed: {
            ...mapGetters(['getProjectById']),
        },
        created () {
            this.project = this.getProjectById((Number)(this.projectId))
        },
        methods: {
            createRisk() {
                this.$router.push({ name: 'RiskCreation', params: { projectId: this.project.id } })
            },
            showRisk(risk) {
                this.$router.push({ name: 'RiskView', params: { projectId: this.project.id, riskId: risk.id } })
            },
            getColor(riskRate) {
                return Risk.convertRiskRateToColor(riskRate)
            },
            showProfile(userId) {
                this.$router.push({ name: 'Profile', params: { userId: userId } })
            },
            getResponsible(risk) {
                if (risk.responsible) {
                    if (risk.responsible.length <= this.maxRiskUsersAmount) {
                        return risk.responsible
                    } else {
                        return risk.responsible.slice(0, this.maxRiskUsersAmount)
                    }
                }
            },
            getExtraRiskUsersAmount(risk) {
                return risk.responsible.length - this.getResponsible(risk).length
            },
            getRiskStatus(risk) {
                return Risk.convertStatus(risk.status)
            }
        },
        components: {
            BubbleChart, DoughnutChart
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
    .cetered-text-span{
        background: #C0C0C0;
        border-radius: 50%;
        height: 100%;
        width: 100%;
        line-height: 250%;
        display: inline-block;
        text-align: center;
    }

</style>