<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-layout align-space-around justify-start column>
        <v-container>
            <span v-for="project in sortedProjects" :key="project.id">
                {{ project.id }}
            </span>
        </v-container>
    </v-layout>
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

