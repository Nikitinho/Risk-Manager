<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-container fluid>
    <v-layout align-space-around justify-start column>
        <v-card>
            <v-list>
                <template v-for="(project, index) in sortedProjects" :v-key="project.id">

                    <v-divider v-if="index > 0"></v-divider>

                    <v-list-tile avatar
                            @click="() => showProject(project)">
                        <v-list-tile-avatar v-if="project.author">
                            <v-img class="elevation-6"
                                   :src="project.author.userpic">
                            </v-img>
                        </v-list-tile-avatar>

                        <v-list-tile-content>
                            <v-list-tile-title v-html="project.description"></v-list-tile-title>
                        </v-list-tile-content>
                    </v-list-tile>
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
            }
        },
        computed: mapGetters(['sortedProjects']),
        methods: {
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

