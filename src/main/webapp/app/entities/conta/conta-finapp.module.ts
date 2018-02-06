import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FinapplicationSharedModule } from '../../shared';
import { FinapplicationAdminModule } from '../../admin/admin.module';
import {
    ContaFinappService,
    ContaFinappPopupService,
    ContaFinappComponent,
    ContaFinappDetailComponent,
    ContaFinappDialogComponent,
    ContaFinappPopupComponent,
    ContaFinappDeletePopupComponent,
    ContaFinappDeleteDialogComponent,
    contaRoute,
    contaPopupRoute,
    ContaFinappResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...contaRoute,
    ...contaPopupRoute,
];

@NgModule({
    imports: [
        FinapplicationSharedModule,
        FinapplicationAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ContaFinappComponent,
        ContaFinappDetailComponent,
        ContaFinappDialogComponent,
        ContaFinappDeleteDialogComponent,
        ContaFinappPopupComponent,
        ContaFinappDeletePopupComponent,
    ],
    entryComponents: [
        ContaFinappComponent,
        ContaFinappDialogComponent,
        ContaFinappPopupComponent,
        ContaFinappDeleteDialogComponent,
        ContaFinappDeletePopupComponent,
    ],
    providers: [
        ContaFinappService,
        ContaFinappPopupService,
        ContaFinappResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FinapplicationContaFinappModule {}
