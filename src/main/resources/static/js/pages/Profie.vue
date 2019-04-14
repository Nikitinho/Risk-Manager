<template>
    <v-container fluid>
        <v-layout align-space-around justify-start wrap>
            <v-flex :xs6="!$vuetify.breakpoint.mdAndDown" class="px-1 py-1">
        <v-card class="mb-1 py-1 px-1">
                <v-layout align-center justify-center row wrap>
                    <v-flex class="px-1" align-self-center>
                        <v-img class="img-circle elevation-6"
                               style="max-width: 200px; max-height: 200px"
                               :src="userProfile.userpic">
                        </v-img>
                    </v-flex>
                    <v-flex justify-start text-xs-center>
                        <v-layout class="px-1" column>
                            <v-divider></v-divider>
                            <v-flex>Name: {{ userProfile.name || 'Name is not provided' }}</v-flex>
                            <v-divider></v-divider>
                            <v-flex>Locale: {{ userProfile.locale || 'Locale is not provided' }}</v-flex>
                            <v-divider></v-divider>
                            <v-flex>Gender: {{ userProfile.gender || 'Gender is not provided' }}</v-flex>
                            <v-divider></v-divider>
                            <v-flex>Email: {{ userProfile.email || 'Email is not provided' }}</v-flex>
                            <v-divider></v-divider>
                            <v-flex>Last action: {{ userProfile.lastVisit || 'Last action is not provided' }}</v-flex>
                            <v-divider></v-divider>
                        </v-layout>
                    </v-flex>
                </v-layout>
        </v-card>
            </v-flex>
            <v-flex :xs6="!$vuetify.breakpoint.mdAndDown" class="px-1 py-1">
        <v-card class="mb-1">
            <v-card-title>
                <span class="headline font-weight-light">Top 10 risks</span>
                <v-spacer></v-spacer>
                <v-btn color="success"
                       @click="() => { this.isTopShown = !this.isTopShown }">
                    {{ isTopShown ? 'Hide' : 'Show' }}
                </v-btn>
            </v-card-title>
            <slot v-if="isTopShown">
            <v-list v-if="getProfileRisks && getProfileRisks.length > 0">
            <template v-for="risk in getProfileRisks.slice(0, 10)"
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
            </slot>
        </v-card>
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
    import { mapGetters, mapActions } from 'vuex'
    import Risk from 'domain/Risk'
    export default {
        name: 'Profile',
        props: ['userId'],
        data() {
            return {
                isTopShown: true
            }
        },
        computed: { 
            ...mapGetters(['getUserById', 'getProfileRisks']),
            userProfile() {
                return this.getUserById(this.userId)
            }
        },
        methods: {
            ...mapActions(['fetchUserRisks']),
            getColor(riskRate) {
                return Risk.convertRiskRateToColor(riskRate)
            },
            getRiskStatus(risk) {
                return Risk.convertStatus(risk.status)
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
            showRisk(risk) {
                this.$router.push({ name: 'RiskView', params: { projectId: risk.project.id, riskId: risk.id } })
            }
        },
        created() {
            this.fetchUserRisks(this.userId)
        }
    }
</script>

<style scoped>
    img {
        max-width: 100%;
        height: auto;
    }
    .img-circle {
        border-radius: 50%;
    }
</style>