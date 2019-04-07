<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-container fluid>
    <v-layout align-space-around justify-start column>
            <v-card v-for="project in sortedProjects" :key="project.id" class="py-2 px-2 mb-1">
                <v-card-title>
                    <span class="title font-weight-bold">
                        {{ project.name }}
                    </span>
                    <v-spacer></v-spacer>
                    <!--<v-divider vertical></v-divider>-->
                    <!--<slot v-for="i in 15">-->
                    <!--<span v-for="user in project.responsible">-->

                    <!--<v-tooltip bottom>-->
                        <!--<template v-slot:activator="{ on }">-->
                            <!--<v-list-tile-avatar color="grey darken-3">-->
                            <!--<v-img class="elevation-6"-->
                                   <!--:src="user.userpic"-->
                                   <!--v-on="on">-->
                            <!--</v-img>-->
                            <!--</v-list-tile-avatar>-->
                        <!--</template>-->
                        <!--<span>{{ user.name }}</span>-->
                    <!--</v-tooltip>-->

                <!--</span>-->
                    <!--</slot>-->
                </v-card-title>
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
        computed: mapGetters(['sortedProjects'])

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

