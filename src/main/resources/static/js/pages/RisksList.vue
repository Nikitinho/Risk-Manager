<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-layout align-space-around justify-start column>
        <v-toolbar class="mt-2" prominent>
            <v-text-field
                    append-icon="mic"
                    flat
                    label="Search"
                    v-model="search"
                    prepend-inner-icon="search"
                    solo-inverted>
            </v-text-field>
            <template v-if="areFiltersApplied" v-slot:extension>
                <v-tabs class="mb-2"
                        centered
                        height = "65%"
                        color="transparent">
                    <v-tab>
                        <v-menu>
                            <template v-slot:activator="{ on }">
                                <v-toolbar-title v-on="on">
                                    <span style="font-size: 75%; white-space:pre-wrap;">{{ category || `Choose category` }}</span>
                                </v-toolbar-title>
                            </template>

                            <v-list>
                                <v-list-tile
                                        v-for="item in riskCategoriesItems"
                                        @click="() => category = item">
                                    <v-list-tile-title v-text="item"></v-list-tile-title>
                                </v-list-tile>
                            </v-list>
                        </v-menu>
                    </v-tab>
                    <v-tab>
                        <v-menu>
                            <template v-slot:activator="{ on }">
                                <v-toolbar-title v-on="on">
                                    <span style="font-size: 75%; white-space:pre-wrap;">{{ status || `Choose status` }}</span>
                                </v-toolbar-title>
                            </template>

                            <v-list>
                                <v-list-tile
                                        v-for="item in riskStatusesItems"
                                        @click="() => status = item">
                                    <v-list-tile-title v-text="item"></v-list-tile-title>
                                </v-list-tile>
                            </v-list>
                        </v-menu>
                    </v-tab>
                </v-tabs>
            </template>
            <v-btn flat round @click="() => this.areFiltersApplied = !this.areFiltersApplied">
                <v-icon dark>filter_list</v-icon>
            </v-btn>
        </v-toolbar>
        <v-container>
            <risk-row v-for="risk in filteredRisks"
                      :key="risk.id" :risk="risk"/>
    </v-container>
    </v-layout>
</template>

<script>
    import { mapGetters } from 'vuex'
    import RiskRow from 'components/risks/RiskRow.vue'
    import Risk from 'domain/Risk'

    export default {
        components: {
          RiskRow
        },
        data() {
            return {
                risk: null,
                search: '',
                status: null,
                category: null,
                areFiltersApplied: false
            }
        },
        computed: {
            ...mapGetters(['sortedRisks', 'riskStatuses', 'riskCategories']),
            riskStatusesItems () {
                let items = this.riskStatuses.slice()
//                items.push({value: null, text: 'Choose risk'})
                return items
            },
            riskCategoriesItems () {
                let items = this.riskCategories.slice()
//                items.push({value: null, text: 'Choose category'})
                return items
            },
            filteredRisks() {
                let risks = this.sortedRisks
                if (this.search) {
                    risks = risks.filter(risk =>
                        (String)(risk.text).includes(this.search) || (String)(risk.description).includes(this.search))
                }

                if (this.status) {
                    risks = risks.filter(risk => Risk.convertStatus(risk.status) === this.status)
                }

                if (this.category) {
                    risks = risks.filter(risk => Risk.convertCategory(risk.category) === this.category)
                }

                return risks
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
</style>