/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { FinapplicationTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { LancamentoFinappDetailComponent } from '../../../../../../main/webapp/app/entities/lancamento/lancamento-finapp-detail.component';
import { LancamentoFinappService } from '../../../../../../main/webapp/app/entities/lancamento/lancamento-finapp.service';
import { LancamentoFinapp } from '../../../../../../main/webapp/app/entities/lancamento/lancamento-finapp.model';

describe('Component Tests', () => {

    describe('LancamentoFinapp Management Detail Component', () => {
        let comp: LancamentoFinappDetailComponent;
        let fixture: ComponentFixture<LancamentoFinappDetailComponent>;
        let service: LancamentoFinappService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinapplicationTestModule],
                declarations: [LancamentoFinappDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    LancamentoFinappService,
                    JhiEventManager
                ]
            }).overrideTemplate(LancamentoFinappDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LancamentoFinappDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LancamentoFinappService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new LancamentoFinapp(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.lancamento).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
