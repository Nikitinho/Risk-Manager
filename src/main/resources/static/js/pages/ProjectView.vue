<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-container fluid>
        <v-layout align-space-around justify-start wrap>
            <v-flex :xs8="!$vuetify.breakpoint.mdAndDown" class="px-1 py-1">
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
                    <v-divider></v-divider>
                    <v-toolbar dense flat>
                        <v-text-field
                                class="elevation-0"
                                prepend-icon="search"
                                v-model="searchField"
                                single-line
                                flat>
                        </v-text-field>
                        <v-btn icon @click="() => showFilters = !showFilters">
                            <v-icon>filter_list</v-icon>
                        </v-btn>
                        <template v-if="showFilters" v-slot:extension>
                            <v-tabs centered fixed-tabs
                                    color="transparent">
                                <v-tabs-slider></v-tabs-slider>

                                <v-tab>
                                    <v-menu>
                                        <template v-slot:activator="{ on }">
                                            <v-toolbar-title v-on="on">
                                                <span style="font-size: 75%; white-space:pre-wrap;">{{ chosenCategory.label }}</span>
                                            </v-toolbar-title>
                                        </template>

                                        <v-list>
                                            <v-list-tile
                                                    v-for="(item, index) in categories"
                                                    @click="() => chosenCategory = item">
                                                <v-list-tile-title>{{ item.label }}</v-list-tile-title>
                                            </v-list-tile>
                                        </v-list>
                                    </v-menu>
                                </v-tab>

                                <v-tab>
                                    <v-menu>
                                        <template v-slot:activator="{ on }">
                                            <v-toolbar-title v-on="on">
                                                <span style="font-size: 75%; white-space:pre-wrap;">{{ chosenStatus.label }}</span>
                                            </v-toolbar-title>
                                        </template>

                                        <v-list>
                                            <v-list-tile
                                                    v-for="(item, index) in statuses"
                                                    @click="() => chosenStatus = item">
                                                <v-list-tile-title>{{ item.label }}</v-list-tile-title>
                                            </v-list-tile>
                                        </v-list>
                                    </v-menu>
                                </v-tab>

                                <v-tab>
                                    <v-menu>
                                        <template v-slot:activator="{ on }">
                                            <v-toolbar-title v-on="on">
                                                <span style="font-size: 75%; white-space:pre-wrap;">{{ chosenLevel.label }}</span>
                                            </v-toolbar-title>
                                        </template>

                                        <v-list>
                                            <v-list-tile
                                                    v-for="(item, index) in levels"
                                                    @click="() => chosenLevel = item">
                                                <v-list-tile-title>{{ item.label }}</v-list-tile-title>
                                            </v-list-tile>
                                        </v-list>
                                    </v-menu>
                                </v-tab>

                            </v-tabs>
                        </template>
                    </v-toolbar>
                    <v-list>
                        <template v-for="risk in filteredRisksList" :v-key="risk.id">

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

                                <v-list-tile-avatar v-if="!risk.responsible || risk.responsible.length === 0">
                                    <span class="cetered-text-span">?</span>
                                </v-list-tile-avatar>

                                <v-list-tile-avatar v-else v-for="user in getResponsible(risk)">
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
            <v-flex :xs4="!$vuetify.breakpoint.mdAndDown" class="px-1 py-1">
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
                maxRiskUsersAmount: 3,
                showFilters: false,
                categories: [
                    {label: 'Риск интеграции', value: 'INTEGRATION'},
                    {label: 'Финансовые риски', value: 'FINANCIAL'},
                    {label: 'Временные риски', value: 'TEMPORARY'},
                    {label: 'Риски персонала', value: 'PERSONNEL'},
                    {label: 'Коммуникационные риски', value: 'COMMUNICATION'},
                    {label: 'Риски поставщиков', value: 'VENDOR'},
                    {label: 'Риски несоответствия качеству', value: 'LACK_OF_QUALITY'},
                    {label: 'Выберите категорию', value: null}
                ],
                chosenCategory: {label: 'Выберите категорию', value: null},
                statuses: [
                    {label: 'Открыт', value: 'CREATED'},
                    {label: 'В работе', value: 'IN_THE_WORK'},
                    {label: 'Закрыт', value: 'CLOSED'},
                    {label: 'Выберите статус', value: null}
                ],
                chosenStatus: {label: 'Выберите статус', value: null},
                levels: [
                    {label: 'Низкий', value: 'LOW'},
                    {label: 'Средний', value: 'MEDIUM'},
                    {label: 'Высокий', value: 'HIGH'},
                    {label: 'Выберите уровень', value: null}
                ],
                chosenLevel: {label: 'Выберите уровень', value: null},
                searchField: ''
            }
        },
        computed: {
            ...mapGetters(['getProjectById']),
            filteredRisksList() {
                let list = this.project.risks
                if (this.searchField.length > 0) {
                    list = list.filter(risk => risk.text.toLowerCase().includes(this.searchField.toLowerCase()))
                }
                if (this.chosenCategory.value !== null) {
                    list = list.filter(risk => risk.category === this.chosenCategory.value)
                }
                if (this.chosenLevel.value !== null) {
                    list = list.filter(risk => risk.riskLevel === this.chosenLevel.value)
                }
                if (this.chosenStatus.value !== null) {
                    list = list.filter(risk => risk.status === this.chosenStatus.value)
                }
                return list
            }
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
        color: white;
    }

</style>