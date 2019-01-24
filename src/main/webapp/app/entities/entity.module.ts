import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { JhipsterSampleApplicationPersonneModule } from './personne/personne.module';
import { JhipsterSampleApplicationPetitionModule } from './petition/petition.module';
import { JhipsterSampleApplicationActiviteModule } from './activite/activite.module';
import { JhipsterSampleApplicationDonModule } from './don/don.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        JhipsterSampleApplicationPersonneModule,
        JhipsterSampleApplicationPetitionModule,
        JhipsterSampleApplicationActiviteModule,
        JhipsterSampleApplicationDonModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationEntityModule {}
