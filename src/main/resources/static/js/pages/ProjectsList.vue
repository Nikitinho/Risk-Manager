<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-container fluid>
        <v-layout align-space-around justify-start column>
            <!--<v-flex xs12 class="pb-1">-->
                <!--<v-btn color="#5C84FE">-->
                    <!--<v-icon color="white">add</v-icon>-->
                <!--</v-btn>-->
                    <!--<v-btn color="error">Error</v-btn>-->
                    <!--<v-btn color="warning">Warning</v-btn>-->
                    <!--<v-btn color="info">Info</v-btn>-->
            <!--</v-flex>-->
            <!--<v-flex xs12>-->
            <v-hover>
            <v-data-table
                    slot-scope="{ hover }"
                    :class="`elevation-${hover ? 12 : 2}`"
                    :headers="headers"
                    :items="filteredProjects">
                <template slot="headerCell" slot-scope="props">
                    <v-tooltip bottom>
                        <template v-slot:activator="{ on }">
          <span v-on="on">
            {{ props.header.text }}
          </span>
                        </template>
                        <span>
          {{ props.header.text }}
        </span>
                    </v-tooltip>
                </template>
                <template v-slot:items="props">
                    <tr @click="showProject(props.item)">
                    <td>{{ props.item.name }}</td>
                    <td>{{ props.item.creationDate }}</td>
                    <td>{{ $t(`project.types.${props.item.type.toLowerCase()}`) }}</td>
                    </tr>
                </template>
            </v-data-table>
            </v-hover>
        <!--<v-card>-->
            <!--<v-list>-->
                <!--<template v-for="(project, index) in filteredProjects"-->
                          <!--:v-key="project.id">-->
                    <!--<v-list-tile avatar-->
                                 <!--@click="() => showProject(project)">-->
                        <!--<v-list-tile-avatar v-if="project.author">-->
                            <!--<v-img class="elevation-6"-->
                                   <!--:src="project.author.userpic">-->
                            <!--</v-img>-->
                        <!--</v-list-tile-avatar>-->

                        <!--<v-list-tile-content>-->
                            <!--<v-list-tile-title v-html="`${project.name} - ${project.description}`"></v-list-tile-title>-->
                        <!--</v-list-tile-content>-->
                    <!--</v-list-tile>-->
                <!--</template>-->
            <!--</v-list>-->
            <!--</v-card>-->
            <!--</v-flex>-->
        </v-layout>
    </v-container>
</template>

<script>
    import { mapGetters } from 'vuex'

    export default {
        data() {
            return {
                types:[],
                searchField: '',
                headers: [
                    {
                        text: this.$t('project.fields.name'),
                        align: 'left',
                        sortable: false,
                        value: 'name'
                    },
                    {
                        text: this.$t('project.fields.creationDate'),
                        value: 'creationDate'
                    },
                    {
                        text: this.$t('project.fields.type'),
                        value: 'type'
                    }
                ]
            }
        },
        props: ['projectTypes'],
        computed: {
            ...mapGetters(['sortedProjects', 'getProjectTypes']),
            filteredProjects() {
                let list = []
                let types = this.projectTypes.filter(type => type.isShown).map(function(type) { return type.value; })
                this.sortedProjects.forEach(function (project) {
                    if (types.includes(project.type))
                        list.push(project)
                })
                return list
            }
        },
        created() {
            this.getProjectTypes.forEach(type => this.types.push({value: type, isShown: false}))
            console.log()
        },
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

