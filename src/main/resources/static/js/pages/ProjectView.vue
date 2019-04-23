<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-container fluid>
        <v-layout align-space-around justify-start wrap>
            <v-flex :xs8="!$vuetify.breakpoint.mdAndDown" class="px-1 py-1">
            <v-card class="mb-1">
                <v-card-title>
                    <span class="headline font-weight-light">{{ project.name }}</span>
                    <v-spacer></v-spacer>
                    <v-menu
                            bottom
                            origin="center center"
                            transition="scale-transition"
                    >
                        <template v-slot:activator="{ on }">
                            <v-btn
                                    color="warning"
                                    dark
                                    v-on="on"
                            >
                                {{$t('risk.export.title')}}
                            </v-btn>
                        </template>

                        <v-list>
                            <v-list-tile
                                    v-for="item in exportOptions"
                                    @click="() => exportProject(item)">
                                <v-list-tile-title>{{$t(`risk.export.${item.toLowerCase()}`)}}</v-list-tile-title>
                            </v-list-tile>
                        </v-list>
                    </v-menu>
                </v-card-title>
                <v-divider></v-divider>
                <v-card-text>
                    {{ project.description }}
                </v-card-text>
            </v-card>
                <v-card>
                    <v-card-title>
                        <span class="headline font-weight-light">{{$t('project.risksList.title')}}</span>
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
                                                <span style="font-size: 75%;">
                                                    {{ chosenStatus.value
                                                    ? $t(`project.statuses.${chosenStatus.value.toLowerCase()}`)
                                                    : $t('project.statuses.default')
                                                    }}
                                                </span>
                                            </v-toolbar-title>
                                        </template>

                                        <v-list>
                                            <v-list-tile
                                                    v-for="(item, index) in statuses"
                                                    @click="() => chosenStatus = item">
                                                <v-list-tile-title>
                                                    {{ item.value
                                                    ? $t(`project.statuses.${item.value.toLowerCase()}`)
                                                    : $t('project.statuses.default')
                                                    }}
                                                </v-list-tile-title>
                                            </v-list-tile>
                                        </v-list>
                                    </v-menu>
                                </v-tab>

                                <v-tab>
                                    <v-menu>
                                        <template v-slot:activator="{ on }">
                                            <v-toolbar-title v-on="on">
                                                <span style="font-size: 75%;">
                                                    {{ chosenLevel.value
                                                    ? $t(`project.levels.${chosenLevel.value.toLowerCase()}`)
                                                    : $t('project.levels.default')
                                                    }}
                                                </span>
                                            </v-toolbar-title>
                                        </template>

                                        <v-list>
                                            <v-list-tile
                                                    v-for="(item, index) in levels"
                                                    @click="() => chosenLevel = item">
                                                <v-list-tile-title>
                                                    {{ item.value
                                                    ? $t(`project.levels.${item.value.toLowerCase()}`)
                                                    : $t('project.levels.default')
                                                    }}
                                                </v-list-tile-title>
                                            </v-list-tile>
                                        </v-list>
                                    </v-menu>
                                </v-tab>

                            </v-tabs>
                        </template>
                    </v-toolbar>
                    <v-list>
                        <template v-for="category in categories">
                            <v-divider></v-divider>

                            <v-list-tile style="background: #F5F5F5" @click="() => category.isShown = !category.isShown">
                                <v-list-tile-avatar>
                                    <v-icon>list</v-icon>
                                </v-list-tile-avatar>
                            <v-list-tile-content>
                                <v-list-tile-title>{{$t(`project.categories.${category.value.toLowerCase()}`)}}</v-list-tile-title>
                            </v-list-tile-content>
                            </v-list-tile>

                            <slot v-if="category.isShown">

                            <template v-if="filteredRisksList.filter(x => x.category === category.value).length === 0">
                                <v-divider></v-divider>
                                <v-list-tile>
                                <v-list-tile-content>
                                    <v-list-tile-title>{{$t('project.risksList.emptyField')}}</v-list-tile-title>
                                </v-list-tile-content>
                                </v-list-tile>
                            </template>

                        <template v-else
                                  v-for="risk in filteredRisksList.filter(x => x.category === category.value)"
                                  :v-key="risk.id">

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
                                    <v-chip>{{$t(`project.statuses.${risk.status.toLowerCase()}`)}}</v-chip>
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
                            </slot>
                        </template>
                    </v-list>
                </v-card>
            </v-flex>
            <v-flex :xs4="!$vuetify.breakpoint.mdAndDown" class="px-1 py-1">
                <v-card class="mb-1">
                    <v-card-title>
                        <span class="headline font-weight-light">{{$t('project.projectTeam.title')}}</span>
                        <v-spacer></v-spacer>
                        <v-btn color="success"
                               @click="() => { this.isTeamShown = !this.isTeamShown }">
                            {{ isTeamShown
                            ? $t('project.projectTeam.hideBtn')
                            : $t('project.projectTeam.showBtn') }}
                        </v-btn>
                    </v-card-title>
                    <v-list v-if="isTeamShown">
                        <template v-for="role in roles">
                            <v-divider></v-divider>

                            <v-list-tile style="background: #F5F5F5" @click="() => role.isShown = !role.isShown">
                                <v-list-tile-avatar>
                                    <v-icon>person</v-icon>
                                </v-list-tile-avatar>
                                <v-list-tile-content>
                                    <v-list-tile-title>{{$t(`project.projectTeam.roles.${role.value.toLowerCase()}`)}}</v-list-tile-title>
                                </v-list-tile-content>
                            </v-list-tile>

                            <slot v-if="role.isShown">

                                <template v-if="!project[role.value] || project[role.value].length === 0">
                                    <v-divider></v-divider>
                                    <v-list-tile>
                                        <v-list-tile-content>
                                            <v-list-tile-title>
                                                {{$t('project.projectTeam.emptyField')}}
                                            </v-list-tile-title>
                                        </v-list-tile-content>
                                    </v-list-tile>
                                </template>
                                <template v-else
                                          v-for="user in project[role.value]">
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
                            </slot>
                        </template>
                    </v-list>
                </v-card>
                <v-card class="mb-1">
                    <v-card-title>
                        <span class="headline font-weight-light">{{$t('project.top10Risks.title')}}</span>
                        <v-spacer></v-spacer>
                        <v-btn color="success"
                               @click="() => { this.isTopShown = !this.isTopShown }">
                            {{ isTopShown
                            ? $t('project.top10Risks.hideBtn')
                            : $t('project.top10Risks.showBtn') }}
                        </v-btn>
                    </v-card-title>
                    <slot v-if="isTopShown">
                    <v-list v-if="top10RisksList && top10RisksList.length > 0">
                            <template v-for="risk in top10RisksList"
                                      :v-key="risk.id">
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
                                        <v-chip>{{$t(`project.statuses.${risk.status.toLowerCase()}`)}}</v-chip>
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
                    <slot v-else>
                        <v-divider></v-divider>
                        <v-card-text>
                            {{$t('project.top10Risks.emptyField')}}
                        </v-card-text>
                    </slot>
                    </slot>
                </v-card>
                <v-card>
                    <v-card-title>
                        <span class="headline font-weight-light">
                            {{$t('project.graph.title')}}
                        </span>
                        <v-spacer></v-spacer>
                        <v-btn icon @click="() => this.isBubbleChartShown = !this.isBubbleChartShown">
                            <v-icon dark>loop</v-icon>
                        </v-btn>
                        <v-btn color="success"
                               @click="() => { this.isGraphShown = !this.isGraphShown }">
                            {{ isGraphShown
                            ? $t('project.graph.hideBtn')
                            : $t('project.graph.showBtn')}}
                        </v-btn>
                    </v-card-title>
                    <v-divider></v-divider>
                    <slot v-if="isGraphShown">
                        <slot v-if="project.risks && project.risks.length > 0">
                            <bubble-chart v-if="isBubbleChartShown"
                                          :data="project.risks"
                                          :highRiskValue="highRiskValue">
                            </bubble-chart>
                            <doughnut-chart v-else :data="project.risks"
                                            :highRiskValue="highRiskValue"></doughnut-chart>
                            <v-card-text>
                                <v-slider
                                        persistent-hint
                                        :max="1.00"
                                        step="0.01"
                                        thumb-label="always"
                                        v-model="highRiskValue"
                                ></v-slider>
                            </v-card-text>
                        </slot>
                        <slot v-else>
                            <v-card-text>
                                {{$t('project.graph.emptyField')}}
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
    import printingRiskMixin from 'mixin/PrintingRiskMixin'
    import xmlExportMixin from 'mixin/xmlExportMixin'
    import Risk from 'domain/Risk'

    export default {
        name: 'ProjectView',
        props: ['projectId'],
        mixins: [printingRiskMixin, xmlExportMixin],
        data() {
            return {
                project: null,
                isTeamShown: true,
                isGraphShown: true,
                isBubbleChartShown: true,
                isTopShown: true,
                maxRiskUsersAmount: 3,
                showFilters: false,
                categories: [
                    {label: 'Риск интеграции', value: 'INTEGRATION', isShown: false},
                    {label: 'Финансовые риски', value: 'FINANCIAL', isShown: false},
                    {label: 'Временные риски', value: 'TEMPORARY', isShown: false},
                    {label: 'Риски персонала', value: 'PERSONNEL', isShown: false},
                    {label: 'Коммуникационные риски', value: 'COMMUNICATION', isShown: false},
                    {label: 'Риски поставщиков', value: 'VENDOR', isShown: false},
                    {label: 'Риски несоответствия качеству', value: 'LACK_OF_QUALITY', isShown: false}
                ],
                statuses: [
                    {label: 'Новый', value: 'CREATED'},
                    {label: 'Открыт', value: 'OPENED'},
                    {label: 'Закрыт', value: 'CLOSED'},
                    {label: 'Статус', value: null}
                ],
                chosenStatus: {label: 'Статус', value: null},
                levels: [
                    {label: 'Низкий', value: 'LOW'},
                    {label: 'Средний', value: 'MEDIUM'},
                    {label: 'Высокий', value: 'HIGH'},
                    {label: 'Уровень', value: null}
                ],
                roles: [
                    {label: 'Менеджеры', value: 'managers', isShown: false},
                    {label: 'Аналитики', value: 'analytics', isShown: false},
                    {label: 'Разработчики', value: 'developers', isShown: false},
                    {label: 'Тестировщики', value: 'testers', isShown: false},
                    {label: 'Роли не установлены', value: 'others', isShown: false}
                ],
                chosenLevel: {label: 'Уровень', value: null},
                searchField: '',
                highRiskValue: 0.68
            }
        },
        computed: {
            ...mapGetters(['getProjectById']),
            filteredRisksList() {
                let list = this.project.risks.sort((a, b) => -(a.riskRate - b.riskRate))
                if (this.searchField.length > 0) {
                    list = list.filter(risk => risk.text.toLowerCase().includes(this.searchField.toLowerCase()))
                }
                if (this.chosenLevel.value !== null) {
                    list = list.filter(risk => risk.riskLevel === this.chosenLevel.value)
                }
                if (this.chosenStatus.value !== null) {
                    list = list.filter(risk => risk.status === this.chosenStatus.value)
                }
                return list
            },
            top10RisksList() {
                if (this.project.risks && this.project.risks.length > 0) {
                    let top10 = [...this.project.risks]
                    return top10.sort((a, b) => -(a.riskRate - b.riskRate)).slice(0, 10)
                }
                return []
            },
            exportOptions() {
                let options = []
                options.push('PDF')
                options.push('XML')
                return options
            }
        },
        created () {
            this.project = this.getProjectById((Number)(this.projectId))
            if (this.project.risks && this.project.risks.length > 0)  {
                this.project.risks.sort((a, b) => -(a.riskRate - b.riskRate))
            } else {
                this.project.risks = []
            }
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
            exportProject(DocumentType) {
                if (DocumentType === 'PDF') {
                    this.saveAllPDF(this.project.risks, this.project.id)
                } else if (DocumentType === 'XML') {
                    this.saveAllXML(this.project.risks, this.project.id)
                }
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