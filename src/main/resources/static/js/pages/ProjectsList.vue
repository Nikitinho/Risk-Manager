<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-container fluid>
    <v-layout align-space-around justify-start column>
        <v-card>
            <v-card-title>
                <span class="headline font-weight-light">{{$t('projectsList.title')}}</span>
                <v-spacer></v-spacer>
                <v-btn color="success" @click="createProject">
                    <v-icon dark>add</v-icon>
                </v-btn>
            </v-card-title>

            <v-list v-if="sortedProjects.length > 0">
                <template v-for="type in types">
                    <v-divider></v-divider>

                    <v-list-tile style="background: #F5F5F5"
                                 @click="() => type.isShown = !type.isShown">
                        <v-list-tile-avatar>
                            <v-icon>list</v-icon>
                        </v-list-tile-avatar>
                        <v-list-tile-content>
                            <v-list-tile-title>{{$t(`project.types.${type.value.toLowerCase()}`)}}</v-list-tile-title>
                        </v-list-tile-content>
                    </v-list-tile>

                    <slot v-if="type.isShown">

                        <template v-if="sortedProjects.filter(project => project.type === type.value).length === 0">
                            <v-divider></v-divider>
                            <v-list-tile>
                                <v-list-tile-content>
                                    <v-list-tile-title>{{$t('projectsList.empty')}}</v-list-tile-title>
                                </v-list-tile-content>
                            </v-list-tile>
                        </template>

                <template v-else
                        v-for="(project, index) in sortedProjects.filter(project => project.type === type.value)"
                          :v-key="project.id">

                    <v-divider></v-divider>

                    <v-list-tile avatar
                            @click="() => showProject(project)">
                        <v-list-tile-avatar v-if="project.author">
                            <v-img class="elevation-6"
                                   :src="project.author.userpic">
                            </v-img>
                        </v-list-tile-avatar>

                        <v-list-tile-content>
                            <v-list-tile-title v-html="`${project.name} - ${project.description}`"></v-list-tile-title>
                        </v-list-tile-content>
                    </v-list-tile>
                </template>
                    </slot>
                </template>
            </v-list>
            </v-card>
    </v-layout>
    </v-container>
</template>

<script>
    import { mapGetters } from 'vuex'

    export default {
        data() {
            return {
                types:[]
            }
        },
        computed: mapGetters(['sortedProjects', 'getProjectTypes']),
        created() {
            this.getProjectTypes.forEach(type => this.types.push({value: type, isShown: false}))
        },
        methods: {
            createProject() {
                this.$router.push({ name: 'ProjectCreation' })
            },
            showProject(project) {
                this.$router.push({ name: 'ProjectView', params: { projectId: project.id } })
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

