import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FinapplicationSharedModule } from '../../shared';
import { FinapplicationAdminModule } from '../../admin/admin.module';
import {
    LancamentoFinappService,
    LancamentoFinappPopupService,
    LancamentoFinappComponent,
    LancamentoFinappDetailComponent,
    LancamentoFinappDialogComponent,
    LancamentoFinappPopupComponent,
    LancamentoFinappDeletePopupComponent,
    LancamentoFinappDeleteDialogComponent,
    lancamentoRoute,
    lancamentoPopupRoute,
} from './';

const ENTITY_STATES = [
    ...lancamentoRoute,
    ...lancamentoPopupRoute,
];

@NgModule({
    imports: [
        FinapplicationSharedModule,
        FinapplicationAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        LancamentoFinappComponent,
        LancamentoFinappDetailComponent,
        LancamentoFinappDialogComponent,
        LancamentoFinappDeleteDialogComponent,
        LancamentoFinappPopupComponent,
        LancamentoFinappDeletePopupComponent,
    ],
    entryComponents: [
        LancamentoFinappComponent,
        LancamentoFinappDialogComponent,
        LancamentoFinappPopupComponent,
        LancamentoFinappDeleteDialogComponent,
        LancamentoFinappDeletePopupComponent,
    ],
    providers: [
        LancamentoFinappService,
        LancamentoFinappPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FinapplicationLancamentoFinappModule {}
