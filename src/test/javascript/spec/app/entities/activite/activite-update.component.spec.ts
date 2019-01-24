/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ActiviteUpdateComponent } from 'app/entities/activite/activite-update.component';
import { ActiviteService } from 'app/entities/activite/activite.service';
import { Activite } from 'app/shared/model/activite.model';

describe('Component Tests', () => {
    describe('Activite Management Update Component', () => {
        let comp: ActiviteUpdateComponent;
        let fixture: ComponentFixture<ActiviteUpdateComponent>;
        let service: ActiviteService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [ActiviteUpdateComponent]
            })
                .overrideTemplate(ActiviteUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ActiviteUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ActiviteService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Activite(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.activite = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Activite();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.activite = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
