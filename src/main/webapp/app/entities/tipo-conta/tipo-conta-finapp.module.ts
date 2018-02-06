import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FinapplicationSharedModule } from '../../shared';
import { FinapplicationAdminModule } from '../../admin/admin.module';
import {
    TipoContaFinappService,
    TipoContaFinappPopupService,
    TipoContaFinappComponent,
    TipoContaFinappDetailComponent,
    TipoContaFinappDialogComponent,
    TipoContaFinappPopupComponent,
    TipoContaFinappDeletePopupComponent,
    TipoContaFinappDeleteDialogComponent,
    tipoContaRoute,
    tipoContaPopupRoute,
    TipoContaFinappResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...tipoContaRoute,
    ...tipoContaPopupRoute,
];

@NgModule({
    imports: [
        FinapplicationSharedModule,
        FinapplicationAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        TipoContaFinappComponent,
        TipoContaFinappDetailComponent,
        TipoContaFinappDialogComponent,
        TipoContaFinappDeleteDialogComponent,
        TipoContaFinappPopupComponent,
        TipoContaFinappDeletePopupComponent,
    ],
    entryComponents: [
        TipoContaFinappComponent,
        TipoContaFinappDialogComponent,
        TipoContaFinappPopupComponent,
        TipoContaFinappDeleteDialogComponent,
        TipoContaFinappDeletePopupComponent,
    ],
    providers: [
        TipoContaFinappService,
        TipoContaFinappPopupService,
        TipoContaFinappResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FinapplicationTipoContaFinappModule {}
